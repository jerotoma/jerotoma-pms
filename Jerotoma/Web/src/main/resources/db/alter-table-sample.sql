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
        
        
ALTER TABLE students 
	ADD COLUMN program_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT programs_fkey FOREIGN KEY (program_id) REFERENCES programs (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
               
ALTER TABLE students 
	ADD COLUMN current_academic_level_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT academic_levels_fkey FOREIGN KEY (current_academic_level_id) REFERENCES academic_levels (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
        
        
        
        
ALTER TABLE courses 
	ADD COLUMN program_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT programs_fkey FOREIGN KEY (program_id) REFERENCES programs (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
               
ALTER TABLE courses 
	ADD COLUMN academic_level_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT academic_levels_fkey FOREIGN KEY (academic_level_id) REFERENCES academic_levels (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;