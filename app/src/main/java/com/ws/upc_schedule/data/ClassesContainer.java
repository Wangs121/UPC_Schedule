package com.ws.upc_schedule.data;

import java.util.List;

public class ClassesContainer {
    private List<CMessage> c_1;
    private List<CMessage> c_2;
    private List<CMessage> c_3;
    private List<CMessage> c_4;
    private List<CMessage> c_5;
    private List<CMessage> c_6;
    private List<CMessage> c_7;
    private List<CMessage> c_8;
    private List<CMessage> c_9;
    private List<CMessage> c_10;
    private List<CMessage> c_11;
    private List<CMessage> c_12;

    public List<CMessage> getc_1() {
        return c_1;
    }

    public List<CMessage> getc_2() {
        return c_2;
    }

    public List<CMessage> getc_3() {
        return c_3;
    }

    public List<CMessage> getc_4() {
        return c_4;
    }

    public List<CMessage> getc_5() {
        return c_5;
    }

    public List<CMessage> getc_6() {
        return c_6;
    }

    public List<CMessage> getc_7() {
        return c_7;
    }

    public List<CMessage> getc_8() {
        return c_8;
    }

    public List<CMessage> getc_9() {
        return c_9;
    }

    public List<CMessage> getc_10() {
        return c_10;
    }

    public List<CMessage> getc_11() {
        return c_11;
    }

    public List<CMessage> getc_12() {
        return c_12;
    }

    public class CMessage {
        private String course_id;
        private String course_name;
        private String location;
        private String weekday;
        private String lessons;
        private String teacher;
        private String week;
        private String week_type;
        private String course_time;
        private int[] lessArr;
        private int order;
        private int totalLength;

        public String get_course_id() {
            return this.course_id;
        }

        public String get_course_name() {
            return this.course_name;
        }

        public String get_location() {
            return this.location;
        }

        public String get_weekday() {
            return this.weekday;
        }

        public String get_lessons() {
            return this.lessons;
        }

        public String get_teacher() {
            return this.teacher;
        }

        public String get_week() {
            return this.week;
        }

        public String get_week_type() {
            return this.week_type;
        }

        public String get_course_time() {
            return this.course_time;
        }

        public int[] get_lessArr() {
            return this.lessArr;
        }

        public int get_order() {
            return this.order;
        }

        public int get_totalLength() {
            return this.totalLength;
        }
    }
}
