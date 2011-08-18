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
            TargetExpList ValueListInOrNotConditionExp WhereExp Exp$DisOrAll
            Exp$Aggregation Exp$MathOp Exp$CompareOp Exp$AnyOrAll Exp$AscOrDesc]))

(defn- enum2keyword [e]
  (-> e .name keyword))

(defn- map-explist [f explist]
  (loop [explist explist result []]
    (if (nil? explist)
      result
      (recur (.getTail explist)
             (conj result (f (.getHead explist)))))))

(defmulti transform class)
(defmethod transform QueryExp [e]
  (let [{:keys [select from] :as selfrom} (transform (.getSelectFrom e))
        {:keys [where group order] :as wgo} (transform (.getOptions e))]
    (merge selfrom wgo)))
(defmethod transform SelectFromExp [e]
  (let [targets (map-explist transform
                             (.getTargetList e))
        from (map-explist #(.getListName %)
                          (.getFromList e))]
    (into {} [[:select targets] [:from from]])))
(defmethod transform ParameterTargetExp [e]
  [(transform (.getParameter e))
   (.getAlias e)])
(defmethod transform AggregationTargetExp [e]
  [(transform (.getAggregationExp e))
   (.getAlias e)])
(defmethod transform ExpressionTargetExp [e]
  [(transform (.getMathExp e))
   (.getAlias e)])
(defmethod transform OptionalExp [e]
  (let [where (map-explist (fn [elt]
                               (map-explist transform elt))
                             (-> e .getWhere .getConditions))
        group (transform (.getGroup e))
        order (transform (.getOrder e))]
    {:where where :group group :order order}))
(defmethod transform ParameterCompareConditionExp [e]
  (vec (map transform [(.getCompareOp e)
                       (.getParameter1 e)
                       (.getParameter2 e)])))
(defmethod transform ConstantCompareConditionExp [e]
  (conj (vec (map transform [(.getCompareOp e)
                             (.getOperand1 e)]))
        (transform  (.getConstant e))))
(defmethod transform RangeConditionExp [e]
  (into [:RANGE
         (not (.getIsNot e))]
        (map transform [(.getParameter e)
                        (.getDownLimit e)
                        (.getUpLimit e)])))
(defmethod transform ValueListInOrNotConditionExp [e]
  (conj [:IN
         (not (.getIsNot e))
         (transform (.getParameter e))]
        (set (map-explist transform (.getValues e)))))
(defmethod transform LikeConditionExp [e]
  [:LIKE
   (not (.getIsNot e))
   (transform (.getParameter e))
   (transform (.getPattern e))])
(defmethod transform IsNotNullConditionExp [e]
  [:NULL (not (.getIsNot e)) (transform (.getParameter e))])
(defmethod transform GroupExp [e]
  [(transform (.getParameter e))
   (map-explist #(map-explist transform %) (.getConditions e))])
(defmethod transform OrderExp [e]
  (vec (map transform [(.getParameter e)
                       (.getAscOrDesc e)])))
(defmethod transform ParameterExp [e]
  [(.getListName e) (.getParameter e)])
(defmethod transform AggregationExp [e]
  (vec (map transform [(.getAggr e)
                       (.getDisOrAll e)
                       (.getParameter e)])))
(defmethod transform MathExp [e]
  (vec (map transform [(.getOperand1 e)
                       (.getMathOp e)
                       (.getOperand2 e)])))
(defmethod transform Enum [e] (enum2keyword e))
(defmethod transform Constant [e] (.getConstant e))
(defmethod transform MatchPattern [e] (.getPattern e))
(defmethod transform nil [e] nil)