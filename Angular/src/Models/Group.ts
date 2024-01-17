import { Student } from "./Student";
import { Teacher } from "./Teacher";

export class Group {

    public constructor(
        public id: number,
        public title: string,        
        public description: string,  
        public students: Student[],
        public teachers: Teacher[]              
    ) { }

}
