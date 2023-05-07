* A student is identified by his/her student number, which is a positive integer.
* Student enlists in one or more sections. The student may have already previously enlisted in other sections.
* The system makes sure that the student cannot enlist in any section that has a conflict with previously enlisted sections.
* A section is identified by its section ID, which is alphanumeric.
* A section is in conflict with another section if the schedules are in conflict.
* Schedules are as follows:  
	+ Days:  
        - Mon/Thu, Tue/Fri, Wed/Sat  
    + Periods:  
        - 8:30am-10am, 10am-11:30, 11:30am-1pm, 1pm-2:30pm, 2:30pm-4pm, 4pm-5:30pm  
* A section has a room.  
    + A room is identified by its room name, which is alphanumeric.
* A room has a maximum capacity.  
    + Section enlistment may not exceed the capacity of a room.  
* A section has a subject.
* A student cannot enlist in a section if any of his currently-enlisted sections has the same subject as that section.