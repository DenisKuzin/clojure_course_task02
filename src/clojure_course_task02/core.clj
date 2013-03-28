(ns clojure-course-task02.core
  (:gen-class))


(defn filter-filename [file regex]
  "Filter filename by regex"
  (let [file-name (.getName file)]
    (if (> (count (re-seq (re-pattern regex) file-name)) 0)
      file-name)))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (let [start-dir (clojure.java.io/file path)
        current-file-seq (file-seq start-dir)
        files-chunks (partition (.availableProcessors (Runtime/getRuntime)) current-file-seq)
        results (pmap (fn [current-chunk] (map (fn [current-file] (filter-filename current-file file-name)) current-chunk)) files-chunks)]
    (remove nil? (flatten results))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun
       (map println (find-files file-name path))
       (shutdown-agents)))))
