package com.jerotoma.database.assemblers.dao.attendances.impl;

public class AssemblerAttendanceConstant {
	
	public static StringBuilder getStudentAttendanceBaseSelectQuery() {		
		return new StringBuilder("SELECT ")
				.append(" sa.id, sa.student_id AS studentId, sa.class_attendance_id AS classAttendanceId, sa.attendance_status_id AS attendanceStatusId, sa.added_by AS addedBy, sa.created_on AS createdOn, sa.updated_on AS updatedOn, ")
				.append(" s.first_name AS firstName, s.last_name AS lastName, ast.name AS statusName,")
				.append(" co.name AS courseName, co.id AS courseId, c.id AS classId,  ca.attendance_date AS attendanceDate, ")				
				.append(" ay.id AS academicYearId, ay.year_of_study AS yearOfStudy, ay.name AS academicYearName ")
				.append(" FROM public.student_attendances sa ")				
				.append("  INNER JOIN public.students s ON s.id = sa.student_id ")
				.append("  INNER JOIN public.attendance_statuses ast ON ast.id = sa.attendance_status_id ")
				.append("  INNER JOIN public.class_attendances ca ON ca.id = sa.class_attendance_id ")
				.append("  INNER JOIN public.classes c ON c.id = ca.class_id ")
				.append("  INNER JOIN public.courses co ON co.id = c.course_id")
				.append("  INNER JOIN public.academic_years ay ON ay.id = c.academic_year_id");
		
	}
	
	public static StringBuilder getClassAttendanceBseSelectQuery() {		
		return new StringBuilder("SELECT ca.id, ca.class_id AS classId, ca.attendance_date AS attendanceDate, ca.added_by AS addedBy, ca.created_on AS createdOn, ca.updated_on AS updatedOn, ")
				.append(" t.id AS teacherId, t.first_name AS firstName, t.last_name AS lastName, ")
				.append(" co.name AS courseName, co.id AS courseId, ")				
				.append(" ay.id AS academicYearId, ay.year_of_study AS yearOfStudy, ay.name AS academicYearName ")
				.append(" FROM public.class_attendances ca ")				
				.append("  INNER JOIN public.classes c ON c.id = ca.class_id ")
				.append("  INNER JOIN public.courses co ON co.id = c.course_id")
				.append("  INNER JOIN public.teachers t ON t.id = c.teacher_id ")						
				.append("  INNER JOIN public.academic_years ay ON ay.id = c.academic_year_id ");
		
	}
	
	public static StringBuilder getStudentAttendedClassBaseSelectQuery() {		
		return new StringBuilder("SELECT ")
				.append(" ca.id AS classAttendanceId, ca.attendance_date AS attendanceDate, ")
				.append(" c.id AS classId,  ")				
				.append(" s.id AS studentId, s.first_name AS firstName, s.last_name AS lastName, ")
				.append(" co.name AS courseName, co.id AS courseId, ")				
				.append(" ay.id AS academicYearId, ay.year_of_study AS yearOfStudy, ay.name AS academicYearName, ")
				.append(" sa.id, sa.attendance_status_id AS attendanceStatusId, sa.added_by AS addedBy, sa.created_on AS createdOn, sa.updated_on AS updatedOn, ")
				.append(" ast.name AS statusName ")
				.append(" FROM public.class_attendances ca ")				
				.append("  INNER JOIN public.classes c ON c.id = ca.class_id ")
				.append("  INNER JOIN public.student_registered_classes src ON src.class_id = ca.class_id ")
				.append("  INNER JOIN public.student_classes sc ON sc.id = src.student_class_id ")
				.append("  INNER JOIN public.students s ON s.id = sc.student_id ")
				.append("  INNER JOIN public.courses co ON co.id = c.course_id")
				.append("  INNER JOIN public.academic_years ay ON ay.id = c.academic_year_id ")
				.append("  LEFT JOIN public.student_attendances sa ON sa.class_attendance_id = ca.id AND sa.student_id = s.id")
				.append("  LEFT JOIN public.attendance_statuses ast ON ast.id = sa.attendance_status_id ");
		
	}
}
