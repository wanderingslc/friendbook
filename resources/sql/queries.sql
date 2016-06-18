-- :name save-message! :! :n
-- creates a new message
INSERT INTO friendbook
(name, message, timestamp)
VALUES (:name, :message, :timestamp)

-- :name get-messages :? :*
-- selects all available messages
SELECT * from friendbook