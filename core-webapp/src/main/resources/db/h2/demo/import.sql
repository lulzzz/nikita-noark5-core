insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'admin', 'admin@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'recordkeeper', 'rm@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'casehandler', 'case_handler@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (4, 'leader', 'leader@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (5, 'guest', 'guest@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');

INSERT INTO nikita_authority (id, authority_name) VALUES (1, 'RECORDS_MANAGER');
INSERT INTO nikita_authority (id, authority_name) VALUES (2, 'RECORDS_KEEPER');
INSERT INTO nikita_authority (id, authority_name) VALUES (3, 'CASE_HANDLER');
INSERT INTO nikita_authority (id, authority_name) VALUES (4, 'LEADER');
INSERT INTO nikita_authority (id, authority_name) VALUES (5, 'GUEST');

insert into nikita_user_authority(user_id, authority_id) values (1, 1);
insert into nikita_user_authority(user_id, authority_id) values (2, 2);
insert into nikita_user_authority(user_id, authority_id) values (3, 3);
insert into nikita_user_authority(user_id, authority_id) values (4, 4);
insert into nikita_user_authority(user_id, authority_id) values (5, 5);

insert into document_medium (pk_document_medium_id, system_id, code, description, deleted, version) values (0, '48e067ae-d794-4216-8a9d-7cb0ef03472c', 'Fysisk medium', 'Bare fysiske dokumenter', false, 0);
insert into document_medium (pk_document_medium_id, system_id, code, description, deleted, version) values (1, '1b617405-9dd6-4187-bee6-81ed4f4a0e1f', 'Elektronisk arkiv', 'Bare elektroniske dokumenter', false, 0);
insert into document_medium (pk_document_medium_id, system_id, code, deleted, version) values (2, '00d21eea-c231-4542-8e3a-964f441fbbd3', 'Blandet fysisk og elektronisk arkiv', false, 0);

insert into fonds_status(pk_fonds_status_id, system_id, code, deleted, version) values (0, '9ed3f5d3-9934-446d-85b8-b2ada9498aa7', 'Opprettet', false, 0);
insert into fonds_status(pk_fonds_status_id, system_id, code, deleted, version) values (1, 'b94eb9cf-e71f-4d68-964b-b68c7f9962b6', 'Avsluttet', false, 0);

insert into series_status(pk_series_status_id, system_id, code, description, deleted, version) values (0, '21dca458-5715-455b-baef-6c81c6e1e73a', 'A', 'Aktiv periode', false, 0);
insert into series_status(pk_series_status_id, system_id, code, description, deleted, version) values (1, '24ee7d87-4e5e-46d8-ba9a-d236609fdeeb', 'O', 'Overlappingsperiode', false, 0);
insert into series_status(pk_series_status_id, system_id, code, description, deleted, version) values (2, 'a67ae905-a556-4cf6-bfa9-43ad15836f61', 'P', 'Avsluttet periode', false, 0);
insert into series_status(pk_series_status_id, system_id, code, description, deleted, version) values (3, '30bec9da-7299-4f50-9233-8a556cc5708c', 'U', 'Uaktuelle mapper', false, 0);

insert into sign_off_method(pk_sign_off_method_id, system_id, code, description, deleted, version) values (0, '5ccea231-c2cc-4507-8a72-709c9c54bcd5', 'BU', 'Besvart med brev', false, 0);
insert into sign_off_method(pk_sign_off_method_id, system_id, code, description, deleted, version) values (1, '3ea3d7c2-2479-46a4-b56d-8cc0c31208a7', 'BE', 'Besvart med e-post', false, 0);
insert into sign_off_method(pk_sign_off_method_id, system_id, code, description, deleted, version) values (2, 'e549d858-f6d6-4d86-b811-014ade0ca6c6', 'TLF', 'Besvart på telefon', false, 0);
insert into sign_off_method(pk_sign_off_method_id, system_id, code, description, deleted, version) values (3, 'ec14aac1-df6d-495a-bcf5-caa9a0c18bb8', 'TE', 'Tatt til etterretning', false, 0);
insert into sign_off_method(pk_sign_off_method_id, system_id, code, description, deleted, version) values (4, 'c62c80d9-7700-4243-b2e1-fd14af9a9559', 'TO', 'Tatt til orientering', false, 0);

insert into document_status(pk_document_status_id, system_id, code, description, deleted, version) values (0, '553287bc-4592-4107-8567-0402458fdc4e', 'B', 'Dokumentet er under redigering', false, 0);
insert into document_status(pk_document_status_id, system_id, code, description, deleted, version) values (1, '29ad0b9a-fe52-4e4b-af9a-1a4841f6ad43', 'F', 'Dokumentet er ferdigstilt', false, 0);

insert into document_type(pk_document_type_id, system_id, code, description, deleted, version) values (0, '26ef04f8-5efb-4f19-9cde-d99a40d8cb1b', 'B', 'Brev', false, 0);
insert into document_type(pk_document_type_id, system_id, code, description, deleted, version) values (1, 'e12f0bf6-aac4-4bb0-8f23-9847f0b67453', 'R', 'Rundskriv', false, 0);
insert into document_type(pk_document_type_id, system_id, code, description, deleted, version) values (2, '1f4ec7b5-a717-433d-a3dc-d357e6579de0', 'F', 'Faktura', false, 0);
insert into document_type(pk_document_type_id, system_id, code, description, deleted, version) values (3, '99281649-0d0c-402f-9494-7df99b7d73f1', 'O', 'Ordrebekreftelse', false, 0);

insert into electronic_signature_security_level(pk_electronic_signature_security_level_id, system_id, code, description, deleted, version) values (0, 'dc55f30f-a549-40ed-86e3-b3104cb1f7f5', 'SK', 'Symmetrisk kryptert', false, 0);
insert into electronic_signature_security_level(pk_electronic_signature_security_level_id, system_id, code, description, deleted, version) values (1, 'e1872a1e-4233-4a6e-884d-d8bdb3c48754', 'V', 'Sendt med PKI/virksomhetssertifikat', false, 0);
insert into electronic_signature_security_level(pk_electronic_signature_security_level_id, system_id, code, description, deleted, version) values (2, '2aa9657b-8498-4d33-8e41-0acf649a8d6d', 'PS', 'Sendt med PKI/"personstandard"-sertifikat', false, 0);
insert into electronic_signature_security_level(pk_electronic_signature_security_level_id, system_id, code, description, deleted, version) values (3, '558401c6-b99e-4adf-9576-811da4c7e461', 'PH', 'Sendt med PKI/"person høy"-sertifikat', false, 0);

insert into electronic_signature_verified(pk_electronic_signature_verified_id, system_id, code, description, deleted, version) values (0, '7440acce-3918-4d0e-92cc-88fe8f1e3168', 'I', 'Signatur påført, ikke verifisert', false, 0);
insert into electronic_signature_verified(pk_electronic_signature_verified_id, system_id, code, description, deleted, version) values (1, '45d2bff7-d2fc-4d22-8b37-74b6d2dd437e', 'V', 'Signatur påført og verifisert', false, 0);

insert into flow_status(pk_flow_status_id, system_id, code, description, deleted, version) values (0, 'd854743a-8c5a-4791-b56c-a10fd240e768', 'G', 'Godkjent', false, 0);
insert into flow_status(pk_flow_status_id, system_id, code, description, deleted, version) values (1, '949ad9cf-59ff-492e-9ce5-8a3bdced9cc9', 'I', 'Ikke godkjent', false, 0);
insert into flow_status(pk_flow_status_id, system_id, code, description, deleted, version) values (2, '8a802fc2-7d9c-43ae-babd-72cc19d47a46', 'S', 'Sendt tilbake til saksbehandler med kommentarer', false, 0);

insert into format (pk_format_id, system_id, code, description, deleted, version) values (0, '93ac2b0f-1729-4c78-a917-a0f494a035ca', 'RA-TEKST', 'Ren tekst', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (1, 'ba4c8c2c-4072-4fa7-b194-8812ea38697f', 'RA-TIFF6', 'TIFF versjon 6', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (2, 'eb53170a-14b1-48e2-a5c4-965fef380ca6', 'RA-PDF', 'PDF/A - ISO 19005-1:2005', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (3, '3d2c0bad-c8e3-4f03-b52e-d99867d53230', 'RA-XML', 'XML', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (4, '18468b06-b491-4029-9e6f-395ad1d27b03', 'RA-JPEG', 'JPEG', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (5, 'd5063523-83a2-4e51-b8aa-925385849e33', 'RA-SOSI', 'SOSI', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (6, '9ae48e86-3c9e-47fd-bbf2-4d9f67b7de85', 'RA-MPEG-2', 'MPEG-2', false, 0);
insert into format (pk_format_id, system_id, code, description, deleted, version) values (7, '7412833e-ceaf-40fd-95b5-e634f4640f99', 'RA-MP3', 'MP3', false, 0);

insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (0, '5b4588a3-7e4d-43ae-a3a8-4033e52944c0', 'EA', 'Avsender', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (1, 'c4a2097c-8cd1-496f-9cf5-bd16de1d85fe', 'EM', 'Mottaker', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (2, '6c2ace2f-05b5-45c0-96da-cff41cc5b2e7', 'EK', 'Kopimottaker', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (3, 'dcb06157-c8d3-4b43-a065-5cc21e80fbf6', 'GM', 'Gruppemottaker', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (4, '896d5f6c-2093-492d-a531-7dd845d45808', 'IA', 'Intern avsender', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (5, '77e1fbc4-e480-4e41-a97e-f429b816629e', 'IM', 'Intern mottaker', false, 0)
insert into correspondence_part_type (pk_correspondence_part_type_id, system_id, code, description, deleted, version) values (6, '78e59b26-819c-4cf7-abe1-69fb6e457bcb', 'IK', 'Intern kopimottaker', false, 0)

insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (0, '0b4cfcb9-e979-4cdd-94d3-1660172976a4', 'J', 'Journalført', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (1, '2f140067-8239-4ea3-88a3-c42380ec3885', 'F', 'Ferdigstilt fra saksbehandler', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (2, '218deb74-f9e0-48e2-a523-3f47a6a44aba', 'G', 'Godkjent av leder', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (3, '35940d96-e531-4e5b-8f0a-63f8c7d08cb0', 'E', 'Ekspedert', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (4, '74aef294-f3d1-4e77-a3c1-abcb45a7eeea', 'A', 'Arkivert', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (5, '8bc27628-f722-41c5-9ce9-4f46fc54bf34', 'U', 'Utgår', false, 0);
insert into registry_entry_status (pk_registry_entry_status_id, system_id, code, description, deleted, version) values (6, '395ff299-e60d-4550-979e-5b682d8e4f61', 'M', 'Midlertidig registrering av', false, 0);


insert into registry_entry_type (pk_registry_entry_type_id, system_id, code, description, deleted, version) values (0, '4827e5e0-0fae-4b9e-a01d-88ce00e8c62e', 'I', 'Inngående dokument', false, 0);
insert into registry_entry_type (pk_registry_entry_type_id, system_id, code, description, deleted, version) values (1, 'f87a8b7f-bb31-4dea-98c6-13e1ac17e24f', 'U', 'Utgående dokument', false, 0);
insert into registry_entry_type (pk_registry_entry_type_id, system_id, code, description, deleted, version) values (2, '001f176e-d68a-42f4-95f8-555a72c1a748', 'N', 'Organinternt dokument for oppfølging', false, 0);
insert into registry_entry_type (pk_registry_entry_type_id, system_id, code, description, deleted, version) values (3, '2014243a-5d33-4fd3-98af-eda7ddd4cdc8', 'X', 'Organinternt dokument uten oppfølging', false, 0);
insert into registry_entry_type (pk_registry_entry_type_id, system_id, code, description, deleted, version) values (4, '07c06822-50e8-47fb-a760-eaff70eaa64e', 'S', 'Saksframlegg', false, 0);
