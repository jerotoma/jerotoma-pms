ALTER TABLE parents DROP COLUMN IF EXISTS avatar;
ALTER TABLE teachers DROP COLUMN IF EXISTS avatar;
ALTER TABLE staffs DROP COLUMN IF EXISTS avatar;
ALTER TABLE students DROP COLUMN IF EXISTS avatar;

ALTER TABLE parents 
	ADD COLUMN profile_image_id bigint,
	ADD CONSTRAINT user_media_fkey FOREIGN KEY (profile_image_id) REFERENCES user_media (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
ALTER TABLE teachers 
	ADD COLUMN profile_image_id bigint,
	ADD CONSTRAINT user_media_fkey FOREIGN KEY (profile_image_id) REFERENCES user_media (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
ALTER TABLE staffs 
	ADD COLUMN profile_image_id bigint,
	ADD CONSTRAINT user_media_fkey FOREIGN KEY (profile_image_id) REFERENCES user_media (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
ALTER TABLE students 
	ADD COLUMN profile_image_id bigint,
	ADD CONSTRAINT user_media_fkey FOREIGN KEY (profile_image_id) REFERENCES user_media (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;