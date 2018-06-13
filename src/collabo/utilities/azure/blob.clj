(ns collabo.utilities.azure.blob
  (:require
   [environ.core :refer [env]]
   [clojure.java.io :as io]
   [collabo.utilities.file :refer [get-file-extension]])
  (:import
   (com.microsoft.azure.storage CloudStorageAccount)
   (java.util UUID)))

(def account-name (env :azure-storage-account-name))
(def account-key (env :azure-storage-account-key))
(def endpoint-suffix (env :azure-storage-endpoint-suffix))

(def storage-connection-string
  (str "DefaultEndpointsProtocol=https;AccountName="
       account-name
       ";AccountKey="
       account-key
       ";EndpointSuffix="
       endpoint-suffix))

(def images-container-name "images")

(defn make-account [connection-string]
  (CloudStorageAccount/parse connection-string))

(defn make-blobclient [account]
  (.createCloudBlobClient account))

(defn make-container-ref [blobclient container-name]
  (.getContainerReference blobclient container-name))

(defn upload [container file blobname]
  (let [blob-ref (.getBlockBlobReference container blobname)]
    (.uploadFromFile blob-ref (.getAbsolutePath file))))

(defn upload-image [file blobname]
  ;; upload file to BLOB Storage
  (let [account (make-account storage-connection-string)
        blobclient (make-blobclient account)
        container-ref (make-container-ref blobclient images-container-name)]
    (upload container-ref file blobname)))

(defn make-random-filename [file]
  (str (UUID/randomUUID)
       "."
       (get-file-extension (.getName file))))

(defn make-user-icon-blobname [{:keys [id] :as user} filename]
  ;; return blobname formated /usericon/:user_id/:filename
  (str "/usericon/" id  "/" filename))
