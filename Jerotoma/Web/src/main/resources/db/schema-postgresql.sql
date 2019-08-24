/**************************************************************
 * 															  *
 * 															  *
 * 			USERS RELATED TABLES					  		  *
 * 															  *
 *************************************************************/

CREATE TABLE IF NOT EXISTS public.users(
    id bigserial NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    enabled boolean NOT NULL,    
    account_non_expired boolean NOT NULL,
	credentials_non_expired boolean NOT NULL, 
	account_non_locked boolean NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
   	CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT username UNIQUE (username),
    CONSTRAINT roles_fkey FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION 
    );

/**************************************************************
 * 															  *
 * 															  *
 * 			ROLES RELATED TABLES					  		  *
 * 															  *
 *************************************************************/

CREATE TABLE IF NOT EXISTS public.roles(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    display_name character varying(255) NOT NULL,    
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    CONSTRAINT name UNIQUE (name),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
    );

/**************************************************************
 * 															  *
 * 															  *
 * 			USER_ROLES RELATED TABLES					  	  *
 * 															  *
 *************************************************************/

CREATE TABLE IF NOT EXISTS public.user_roles(
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    created_on timestamp with time zone NOT NULL DEFAULT 'now()',
    updated_on timestamp with time zone NOT NULL DEFAULT 'now()',
    CONSTRAINT user_roles_pkey PRIMARY KEY (id),
    CONSTRAINT roles_fkey FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE   
    );
    
 /**************************************************************
 * 															  *
 * 															  *
 * 			POSITIONS RELATED TABLES					  		  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.positions(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL,
    description text NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    CONSTRAINT positions_ukey UNIQUE (code),
   	CONSTRAINT positions_pkey PRIMARY KEY (id)
    );
    
    
 /**************************************************************
 * 															  *
 * 															  *
 * 			FIELD_OF_STUDIES RELATED TABLES					  	  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.academic_disciplines(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL,
    description text NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    CONSTRAINT field_of_studies_ukey UNIQUE (code),
   	CONSTRAINT field_of_studies_pkey PRIMARY KEY (id)
    );
    
    
/**************************************************************
 * 															  *
 * 															  *
 * 			TEACHERS RELATED TABLES					  		  *
 * 															  *
 *************************************************************/
CREATE TABLE IF NOT EXISTS public.teachers(
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    position_id bigint NOT NULL,
    academic_discipline_id bigint NOT NULL,
    teacher_code character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    middle_names text,
    phone_number character varying(255) NOT NULL,
    email_address character varying(255),
    occupation character varying(255) NOT NULL,
    gender character varying(25) NOT NULL,
    avatar character varying(255) NOT NULL,    
    birth_date timestamp with time zone,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    updated_by bigint NOT NULL,
   	CONSTRAINT teachers_pkey PRIMARY KEY (id),
   	CONSTRAINT user_id UNIQUE (user_id), 
   	CONSTRAINT teacher_code UNIQUE (teacher_code),
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT positions_fkey FOREIGN KEY (position_id)
        REFERENCES public.positions(id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
   CONSTRAINT academic_disciplines_fkey FOREIGN KEY (academic_discipline_id)
        REFERENCES public.academic_disciplines (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE 
    );

/**************************************************************
 * 															  *
 * 															  *
 * 			PARENTS RELATED TABLES					  		  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.parents(
    id bigserial NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    middle_names text,
    phone_number character varying(255),
    email_address character varying(255),
    occupation character varying(255) NOT NULL,
    gender character varying(25) NOT NULL,
    avatar character varying(255) NOT NULL,
    birth_date timestamp with time zone,
    updated_by bigint NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
   	CONSTRAINT parents_pkey PRIMARY KEY (id)
   	);
 
/**************************************************************
 * 															  *
 * 															  *
 * 			STUDENTS RELATED TABLES					  		  *
 * 															  *
 *************************************************************/

CREATE TABLE IF NOT EXISTS public.students(
    id bigserial NOT NULL,
    parent_id bigint NOT NULL,
    student_number bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    middle_names text,
    email_address character varying(255),
    phone_number character varying(255) NOT NULL,
    occupation character varying(255),
    gender character varying(25) NOT NULL,
    avatar character varying(255) NOT NULL,
    position character varying(255),
    birth_date timestamp with time zone NOT NULL,
    updated_by bigint NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
   	CONSTRAINT students_pkey PRIMARY KEY (id),
   	CONSTRAINT student_number UNIQUE (student_number),
   	CONSTRAINT parents_fkey FOREIGN KEY (parent_id)
        REFERENCES public.parents (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE 
    );

/**************************************************************
 * 															  *
 * 															  *
 * 			OTHER_STAFFS RELATED TABLES					  	  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.staffs(
    id bigserial NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    middle_names text,
    phone_number character varying(255) NOT NULL,
    email_address character varying(255),
    occupation character varying(255) NOT NULL,
    gender character varying(25) NOT NULL,
    avatar character varying(255) NOT NULL,
    position character varying(255) NOT NULL,
    birth_date timestamp with time zone,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    updated_by bigint NOT NULL,
   	CONSTRAINT other_staffs_pkey PRIMARY KEY (id)
    );

/**************************************************************
 * 															  *
 * 															  *
 * 			COURSES RELATED TABLES					  		  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.courses(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL,
    description text NOT NULL,
    updated_by bigint NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    CONSTRAINT courses_ukey UNIQUE (code),
   	CONSTRAINT courses_pkey PRIMARY KEY (id)
    );
    
    
/**************************************************************
 * 															  *
 * 															  *
 * 			COURSE_ADMISSIONS RELATED TABLES				  *
 * 															  *
 *************************************************************/
    
CREATE TABLE IF NOT EXISTS public.student_admissions(
    id bigserial NOT NULL,
    student_id bigint NOT NULL,
   	course_id bigint NOT NULL,
   	teacher_id bigint NOT NULL,
   	year_of_study character varying(255) NOT NULL,
   	code character varying(255) NOT NULL,
   	updated_by bigint NOT NULL,
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
   	CONSTRAINT student_admissions_pkey PRIMARY KEY(id),
   	CONSTRAINT student_admissions_ukey UNIQUE (code),
   	CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES public.courses (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT students_fkey FOREIGN KEY (student_id)
        REFERENCES public.students (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT teachers_fkey FOREIGN KEY (teacher_id)
        REFERENCES public.teachers (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
    );

    
	/**************************************************************
	 * 															  *
	 * 															  *
	 * 			ADDRESSES RELATED TABLES				  		  *
	 * 															  *
	 *************************************************************/
	    
	CREATE TABLE IF NOT EXISTS public.addresses(
	    id bigserial NOT NULL,
	    street text NOT NULL,
	   	unit character varying(255),
	   	city character varying(255) NOT NULL,
	   	country character varying(255) NOT NULL,
	   	state character varying(255) NOT NULL,
	   	postal_code character varying(255),
	   	updated_by bigint NOT NULL,
	   	created_on timestamp with time zone NOT NULL,
	    updated_on timestamp with time zone NOT NULL,
	   	CONSTRAINT addresses_pkey PRIMARY KEY(id)   	
    );

    
    /**************************************************************
	 * 															  *
	 * 															  *
	 * 			TEACHER_ADDRESSES RELATED TABLES				  *
	 * 															  *
	 *************************************************************/
	    
	CREATE TABLE IF NOT EXISTS public.teacher_addresses(
	    id bigserial NOT NULL,
	    teacher_id bigint NOT NULL,
	   	address_id bigint NOT NULL,
	   	created_on timestamp with time zone NOT NULL,
	    updated_on timestamp with time zone NOT NULL,
	   	CONSTRAINT teacher_addresses_pkey PRIMARY KEY(id),
	   	CONSTRAINT addresses_fkey FOREIGN KEY (address_id)
	        REFERENCES public.addresses (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE,
	   	CONSTRAINT teachers_fkey FOREIGN KEY (teacher_id)
	        REFERENCES public.teachers (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE
	    );
 	/**************************************************************
	 * 															  *
	 * 															  *
	 * 			STUDENT_ADDRESSES RELATED TABLES				  *
	 * 															  *
	 *************************************************************/
	    
	CREATE TABLE IF NOT EXISTS public.student_addresses(
	    id bigserial NOT NULL,
	    student_id bigint NOT NULL,
	   	address_id bigint NOT NULL,
	   	created_on timestamp with time zone NOT NULL,
	    updated_on timestamp with time zone NOT NULL,
	   	CONSTRAINT student_addresses_pkey PRIMARY KEY(id),
	   	CONSTRAINT addresses_fkey FOREIGN KEY (address_id)
	        REFERENCES public.addresses (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE,
	   	CONSTRAINT students_fkey FOREIGN KEY (student_id)
	        REFERENCES public.students (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE
	    );
	    
	 /**************************************************************
	 * 															  *
	 * 															  *
	 * 			STAFF_ADDRESSES RELATED TABLES				      *
	 * 															  *
	 *************************************************************/
	    
	CREATE TABLE IF NOT EXISTS public.staff_addresses(
	    id bigserial NOT NULL,
	    staff_id bigint NOT NULL,
	   	address_id bigint NOT NULL,
	   	created_on timestamp with time zone NOT NULL,
	    updated_on timestamp with time zone NOT NULL,
	   	CONSTRAINT staff_addresses_pkey PRIMARY KEY(id),
	   	CONSTRAINT addresses_fkey FOREIGN KEY (address_id)
	        REFERENCES public.addresses (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE,
	   	CONSTRAINT staffs_fkey FOREIGN KEY (staff_id)
	        REFERENCES public.staffs (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE
	    );
	    
	/**************************************************************
	 * 															  *
	 * 															  *
	 * 			PARENT_ADDRESSES RELATED TABLES				      *
	 * 															  *
	 *************************************************************/
	    
	CREATE TABLE IF NOT EXISTS public.parent_addresses(
	    id bigserial NOT NULL,
	    parent_id bigint NOT NULL,
	   	address_id bigint NOT NULL,
	   	created_on timestamp with time zone NOT NULL,
	    updated_on timestamp with time zone NOT NULL,
	   	CONSTRAINT parent_addresses_pkey PRIMARY KEY(id),
	   	CONSTRAINT addresses_fkey FOREIGN KEY (address_id)
	        REFERENCES public.addresses (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE,
	   	CONSTRAINT parents_fkey FOREIGN KEY (parent_id)
	        REFERENCES public.parents (id) MATCH SIMPLE
	        ON UPDATE CASCADE
	        ON DELETE CASCADE
	    );

	    
	 /**************************************************************
	 * 															  *
	 * 															  *
	 * 			STUDENT_SEQUENCE RELATED SEQUENCE				  *
	 * 															  *
	 *************************************************************/
	    
	 CREATE SEQUENCE IF NOT EXISTS public.seq_student_numbers INCREMENT 1 START 1000 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;
