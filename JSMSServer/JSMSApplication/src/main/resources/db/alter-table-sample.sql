ALTER TABLE parents DROP COLUMN IF EXISTS avatar;
ALTER TABLE teachers DROP COLUMN IF EXISTS avatar;
ALTER TABLE staffs DROP COLUMN IF EXISTS avatar;
ALTER TABLE students DROP COLUMN IF EXISTS avatar;

ALTER TABLE parents 
	ADD COLUMN profile_image_id bigint,
	ADD CONSTRAINT user_media_fkey FOREIGN KEY (profile_image_id) REFERENCES user_media (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
        
ALTER TABLE parents ADD COLUMN relationship_type character varying(255),
	
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
	ADD COLUMN primary_parent_id bigint,
	ADD CONSTRAINT parents_fkey FOREIGN KEY (primary_parent_id) REFERENCES parents (id) MATCH SIMPLE
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
        
ALTER TABLE program_academic_levels 
	ADD COLUMN completion_order_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT completion_orders_fkey FOREIGN KEY (completion_order_id) REFERENCES completion_orders (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
        
ALTER TABLE student_classes
	ADD COLUMN academic_level_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT academic_level_fkey FOREIGN KEY (academic_level_id) REFERENCES academic_levels (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
      
ALTER TABLE student_classes ADD COLUMN score bigint NOT NULL DEFAULT 0;

ALTER TABLE student_classes
	ADD COLUMN score_standing_id bigint NOT NULL DEFAULT 1,
	ADD CONSTRAINT score_standings_fkey FOREIGN KEY (score_standing_id) REFERENCES score_standings (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE;
      

