drop table tran_exception_log;
create table tran_exception_log (
   act_typ integer,
   error_type text,
   class text,
   message text,
   error_time timestamp without time zone,
   aud_xml text,
   aud_replication text,
   build text,
   stackTrace text
);
