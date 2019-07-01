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

CREATE TABLE IF NOT EXISTS public.roles(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    display_name character varying(255) NOT NULL,    
    created_on timestamp with time zone NOT NULL,
    updated_on timestamp with time zone NOT NULL,
    CONSTRAINT name UNIQUE (name),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
    );

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

    