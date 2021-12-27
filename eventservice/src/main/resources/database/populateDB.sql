

CALL task_status_insert_procedure (
                            TEXT 'Unknown',
                            TEXT 'Gray'
                          );


CALL task_status_insert_procedure (
                            TEXT 'Getting ready',
                            TEXT 'Yellow'
                          );


CALL task_status_insert_procedure (
                            TEXT 'Ready',
                            TEXT 'Green'
                          );

CALL task_status_insert_procedure (
                            TEXT 'Cancelled',
                            TEXT 'Red'
                          );

CALL task_insert_procedure (--BIGINT '1',
                          TEXT 'Buy scissors',
                          TEXT 'We need it to cut duct tape',
                          TIMESTAMP '2022-02-04 12:00',
                          BIGINT '2'
                          );

CALL task_insert_procedure (--BIGINT '1',
                          TEXT 'Buy duct tape',
                          TEXT 'We need it to stick paper',
                          TIMESTAMP '2022-02-02 22:40',
                          BIGINT '2'
                          );

CALL task_insert_procedure (--BIGINT '1',
                          TEXT 'Buy paper',
                          NULL,
                          TIMESTAMP '2022-02-04 12:00',
                          BIGINT '3'
                          );

CALL task_insert_procedure (--BIGINT '2',
                          TEXT 'Buy axe',
                          TEXT 'To chop wood for campfire',
                          TIMESTAMP '2021-12-12 22:00',
                          BIGINT '2'
                          );

CALL task_insert_procedure (--BIGINT '2',
                          TEXT 'Buy pot',
                          NULL,
                          TIMESTAMP '2021-12-12 12:00',
                          BIGINT '3'
                          );

CALL task_insert_procedure (--BIGINT '2',
                          TEXT 'Buy tent',
                          TEXT 'We need it to stick paper',
                          TIMESTAMP '2021-12-12 22:00',
                          BIGINT '2'
                          );

CALL task_insert_procedure (--BIGINT '2',
                          TEXT 'Buy car',
                          TEXT 'To transport things. Cancelled due to cost',
                          TIMESTAMP '2021-12-14 12:00',
                          BIGINT '4'
                          );


CALL event_status_insert_procedure (
                            TEXT 'Unknown',
                            TEXT 'Gray'
                          );


CALL event_status_insert_procedure (
                            TEXT 'Preparing',
                            TEXT 'Yellow'
                          );

CALL event_status_insert_procedure (
                            TEXT 'Online',
                            TEXT 'Green'
                          );


CALL event_status_insert_procedure (
                            TEXT 'Completed',
                            TEXT 'DarkGreen'
                          );

CALL event_status_insert_procedure (
                            TEXT 'Cancelled',
                            TEXT 'Red'
                          );

CALL event_insert_procedure (
                          TEXT 'RockFest',
                          TEXT 'Stadium',
                          TIMESTAMP '2022-02-02 12:00',
                          BIGINT '2'
                          );

CALL event_insert_procedure (
                          TEXT 'Football tournament',
                          TEXT 'Football field',
                          TIMESTAMP '2022-03-03 16:00',
                          BIGINT '5'
                          );