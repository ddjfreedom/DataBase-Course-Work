(ns querymanager.transform
  (:import [querymanager.exps
            AggregationExp AggregationTargetExp CompareConditionExp
            ConditionExp ConditionExpList ConditionExpLists Constant
            ConstantCompareConditionExp ConstantList ConstantRangeConditionExp
            ExistConditionExp Exp ExpressionTargetExp FromExp FromExpList GroupExp
            InOrNotConditionExp IsNotNullConditionExp LikeConditionExp MatchPattern
            MathExp OptionalExp OrderExp ParameterCompareConditionExp ParameterExp
            ParameterRangeConditionExp ParameterTargetExp QueryCompareConditionExp
            QueryExp QueryInOrNotConditionExp QueryRangeConditionExp QueryResult
            RangeConditionExp Result SelectFromExp SetQueryExp TargetExp
            TargetExpList ValueListInOrNotConditionExp WhereExp CreateExp
            TableElementTypeExp ElementDataType ConstantInsertExp Exp$DisOrAll
            Exp$Aggregation Exp$MathOp Exp$CompareOp Exp$AnyOrAll Exp$AscOrDesc
            Exp$DataType Exp$Restrict]))

(defn- enum2keyword [e]
  (-> e .name keyword))

(defn- map-explist [f explist]
  (loop [explist explist result []]
    (if (nil? explist)
      result
      (recur (.getTail explist)
             (conj result (f (.getHead explist)))))))


(defmulti java2cljmap
  "Convert java class hierarchy to clojure data structure"
  class)
(defmethod java2cljmap SetQueryExp [e]
  (let [setop (.getOption e)]
    [:SET (cond (= setop SetQueryExp/EXCEPT) :EXCEPT
                (= setop SetQueryExp/UNION) :UNION
                (= setop SetQueryExp/INTERSECT) :INTERSECT)
     (-> e .getQuery1 java2cljmap)
     (-> e .getQuery2 java2cljmap)]))
(defmethod java2cljmap QueryExp [e]
  (let [{:keys [select from] :as selfrom} (java2cljmap (.getSelectFrom e))
        {:keys [where group order] :as wgo}
        (java2cljmap (.getOptions e))]
    [:query (merge selfrom wgo)]))
(defmethod java2cljmap SelectFromExp [e]
  (let [targets (map-explist java2cljmap (.getTargetList e))
        from (map-explist java2cljmap (.getFromList e))]
    {:select targets, :from from}))
(defmethod java2cljmap ParameterTargetExp [e]
  (let [para (.getParameter e)]
    (if (= (.getParameter para) ParameterExp/STAR)
      :STAR
      [(java2cljmap (.getParameter e))
       (.getAlias e)])))
(defmethod java2cljmap AggregationTargetExp [e]
  [[:AGGR
    (java2cljmap (.getAggregationExp e))]
   (.getAlias e)])
(defmethod java2cljmap ExpressionTargetExp [e]
  [(java2cljmap (.getMathExp e))
   (.getAlias e)])
(defmethod java2cljmap FromExp [e]
  (let [table-name (.getListName e)
        alias (.getAlias e)]
    {:table table-name,
     :query (-> e .getQuery java2cljmap),
     :alias (if (nil? alias) table-name alias)}))
(defmethod java2cljmap OptionalExp [e]
  (let [where (.getWhere e)
        group (.getGroup e)
        order (.getOrder e)]
    {:where (when where
              (map-explist #(map-explist java2cljmap %) (.getConditions where)))
     :groupby (when group (java2cljmap group))
     :order (when order (java2cljmap order))}))
(defmethod java2cljmap ParameterCompareConditionExp [e]
  (vec (map java2cljmap [(.getCompareOp e)
                       (.getParameter1 e)
                       (.getParameter2 e)])))
(defmethod java2cljmap ConstantCompareConditionExp [e]
  (conj (vec (map java2cljmap [(.getCompareOp e)
                             (.getOperand1 e)]))
        (java2cljmap  (.getConstant e))))
(defmethod java2cljmap QueryCompareConditionExp [e]
  (into [:QUERY]
        (map #(java2cljmap %) [(.getCompareOp e)
                               (.getParameter e)
                               (.getAnyOrAll e)
                               (.getQuery e)])))
(defmethod java2cljmap RangeConditionExp [e]
  (let [base [:RANGE (if (.getIsNot e) not identity)]]
    (into (if (instance? e QueryRangeConditionExp)
            (into [:QUERY] base)
            base)
          (map java2cljmap [(.getParameter e)
                            (.getDownLimit e)
                            (.getUpLimit e)]))))
(defmethod java2cljmap ValueListInOrNotConditionExp [e]
  (conj [:IN
         (if (.getIsNot e) not identity)
         (java2cljmap (.getParameter e))]
        (set (map-explist java2cljmap (.getValues e)))))
(defmethod java2cljmap QueryInOrNotConditionExp [e]
  [:QUERY :IN
   (if (.getIsNot e) not identity)
   (-> e .getParameter java2cljmap)
   (-> e .getQuery java2cljmap)])
(defmethod java2cljmap LikeConditionExp [e]
  [:LIKE
   (if (.getIsNot e) not identity)
   (java2cljmap (.getParameter e))
   (java2cljmap (.getPattern e))])
(defmethod java2cljmap IsNotNullConditionExp [e]
  [:NULL (not (.getIsNot e)) (java2cljmap (.getParameter e))])
(defmethod java2cljmap GroupExp [e]
  [(java2cljmap (.getParameter e))
   (map-explist #(map-explist java2cljmap %) (.getConditions e))])
(defmethod java2cljmap OrderExp [e]
  (vec (map java2cljmap [(.getParameter e)
                       (.getAscOrDesc e)])))
(defmethod java2cljmap ParameterExp [e]
  [(.getListName e) (.getParameter e)])
(defmethod java2cljmap AggregationExp [e]
  (vec (map java2cljmap [(.getAggr e)
                       (.getDisOrAll e)
                       (.getParameter e)])))
(defmethod java2cljmap MathExp [e]
  (vec (map java2cljmap [(.getMathOp e)
                         (.getOperand1 e)
                         (.getOperand2 e)])))
(defmethod java2cljmap Enum [e] (enum2keyword e))
(defmethod java2cljmap Constant [e] (.getConstant e))
(defmethod java2cljmap MatchPattern [e] (.getPattern e))
(defmethod java2cljmap nil [e] nil)

(defmethod java2cljmap CreateExp [e]
  [:createtable (.getTableName e)
   (map-explist java2cljmap (.getElements e))])
(defmethod java2cljmap TableElementTypeExp [e]
  {:attr (.getParameterName e),
   :type (-> e .getElementType java2cljmap),
   :restrict (-> e .getRestrict java2cljmap)})
(defmethod java2cljmap ElementDataType [e]
  [(-> e .getDataType java2cljmap)
   (.getNum e)])

(defmethod java2cljmap ConstantInsertExp [e]
  [:insert (.getListName e)
   (when-let [p-list (.getParameterList e)]
     (map-explist java2cljmap p-list))
   (map-explist java2cljmap (.getValues e))])

(defn transform [expr]
  (let [{:keys [select from where group order] :as query} (java2cljmap expr)]
    query))
