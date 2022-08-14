--Volodymyr Vivaldo 0124
insert into tb_card(pan, expiry, created_at, created_by)
values ('NDQ0MDM2NTg1MDA0OTAxMw==', 'MDEyNA==', current_timestamp, 'SYSTEM');
--4440365850049013
insert into tb_client (email, name, created_at, created_by)
values ('some@mail.com', 'Vm9sb2R5bXlyIFZpdmFsZG8=', current_timestamp, 'SYSTEM');

insert into tb_transaction(invoice, amount, currency, card_id, cardholder_id, created_at, created_by)
values (1, 100, 'USD', 'NDQ0MDM2NTg1MDA0OTAxMw==', 'some@mail.com', current_timestamp, 'SYSTEM');