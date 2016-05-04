SELECT /*+ PARALLEL(AUTO) */
  NULL AS CONSTRAINT_CATALOG,
  TABLE_CONTRAINTS.OWNER AS CONSTRAINT_SCHEMA,
  TABLE_CONTRAINTS.CONSTRAINT_NAME,
  NULL AS TABLE_CATALOG,
  TABLE_CONTRAINTS.OWNER AS TABLE_SCHEMA,
  TABLE_CONTRAINTS.TABLE_NAME,
  COLUMNS.COLUMN_NAME,
  COLUMNS.POSITION AS ORDINAL_POSITION
FROM
  ALL_CONSTRAINTS TABLE_CONTRAINTS
  INNER JOIN ALL_CONS_COLUMNS COLUMNS
    ON TABLE_CONTRAINTS.OWNER = COLUMNS.OWNER 
      AND TABLE_CONTRAINTS.TABLE_NAME = COLUMNS.TABLE_NAME 
      AND TABLE_CONTRAINTS.CONSTRAINT_NAME = COLUMNS.CONSTRAINT_NAME
  INNER JOIN ALL_USERS USERS
    ON TABLE_CONTRAINTS.OWNER = TABLE_CONTRAINTS.OWNER  
WHERE
  TABLE_CONTRAINTS.OWNER NOT IN 
    ('ANONYMOUS', 'APEX_PUBLIC_USER', 'APPQOSSYS', 'BI', 'CTXSYS', 'DBSNMP', 'DIP', 
    'EXFSYS', 'FLOWS_30000', 'FLOWS_FILES', 'HR', 'IX', 'LBACSYS', 
    'MDDATA', 'MDSYS', 'MGMT_VIEW', 'OE', 'OLAPSYS', 'ORACLE_OCM', 
    'ORDPLUGINS', 'ORDSYS', 'OUTLN', 'OWBSYS', 'PM', 'SCOTT', 'SH', 
    'SI_INFORMTN_SCHEMA', 'SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', 
    'SYS', 'SYSMAN', 'SYSTEM', 'TSMSYS', 'WKPROXY', 'WKSYS', 'WK_TEST', 
    'WMSYS', 'XDB', 'XS$NULL', 'RDSADMIN')  
  AND NOT REGEXP_LIKE(TABLE_CONTRAINTS.OWNER, '^APEX_[0-9]{6}$')
  AND NOT REGEXP_LIKE(TABLE_CONTRAINTS.OWNER, '^FLOWS_[0-9]{5,6}$')
  AND REGEXP_LIKE(TABLE_CONTRAINTS.OWNER, '${schemas}')
  AND TABLE_CONTRAINTS.TABLE_NAME NOT LIKE 'BIN$%'
  AND TABLE_CONTRAINTS.CONSTRAINT_TYPE IN ('C', 'U', 'P', 'R')
