SELECT /*+ PARALLEL(AUTO) */
  NULL AS SYNONYM_CATALOG,
  SYNONYMS.OWNER AS SYNONYM_SCHEMA,
  SYNONYMS.SYNONYM_NAME,
  NULL AS REFERENCED_OBJECT_CATALOG,
  SYNONYMS.TABLE_OWNER AS REFERENCED_OBJECT_SCHEMA,
  SYNONYMS.TABLE_NAME AS REFERENCED_OBJECT_NAME
FROM
  ALL_SYNONYMS SYNONYMS
  INNER JOIN ALL_USERS USERS
    ON SYNONYMS.OWNER = USERS.USERNAME
WHERE
  USERS.ORACLE_MAINTAINED != 'Y'
  AND NOT REGEXP_LIKE(USERS.USERNAME, '^APEX_[0-9]{6}$')
  AND NOT REGEXP_LIKE(USERS.USERNAME, '^FLOWS_[0-9]{6}$')
  AND NOT REGEXP_LIKE(USERS.USERNAME, '^FLOWS_[0-9]{5}$')
  AND SYNONYMS.TABLE_NAME NOT LIKE 'BIN$%'
ORDER BY
  SYNONYM_SCHEMA,
  SYNONYM_NAME
