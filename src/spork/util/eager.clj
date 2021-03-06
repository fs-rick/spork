;;A set of operations that address some incredibly 
;;slow ops we find when using clojure's lazy 
;;infrastructure.

;;Update Feb 2017 - Marked DEPRECATED.  Most of the
;;performance issues addressed here are no longer relevant.
(ns spork.util.eager)

(defn keys! 
  "Optimized replacement for clojure.core/keys"
  [m]
  (reduce-kv (fn [^java.util.ArrayList acc k v] (doto acc (.add k)))
             (java.util.ArrayList.)
             m))

(defn vals! [^clojure.lang.IPersistentMap m]
  (reduce-kv (fn [^java.util.ArrayList acc k v] (doto acc (.add v)))
             (java.util.ArrayList.)
             m)) 

(defn zipmap! [ks vs]
  (loop [acc (transient {})
         ks ks 
         vs vs]
    (if-let [k (first ks)]
      (if-let [v (first vs)]
        (recur (assoc! acc k v) (rest ks) (rest vs))
        (persistent! acc))
      (persistent! acc))))
  
  
  
