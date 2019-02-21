package com.liuheonline.la.entity;

import java.util.List;

public class PropertiyEntity {

    /**
     * id : 1
     * title : 波色
     * pid : 0
     * sort : 1
     * _child : [{"id":7,"title":"红波","pid":1,"sort":0,"_child":[{"id":1177,"year":"2018","code":1,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1178,"year":"2018","code":2,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1179,"year":"2018","code":7,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1180,"year":"2018","code":8,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1181,"year":"2018","code":12,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1182,"year":"2018","code":13,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1183,"year":"2018","code":18,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1184,"year":"2018","code":19,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1185,"year":"2018","code":23,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1186,"year":"2018","code":24,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1187,"year":"2018","code":29,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1188,"year":"2018","code":30,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1189,"year":"2018","code":34,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1190,"year":"2018","code":35,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1191,"year":"2018","code":40,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1192,"year":"2018","code":45,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1193,"year":"2018","code":46,"attr":"红波","attr_id":7,"color":null,"color_id":0}]},{"id":8,"title":"蓝波","pid":1,"sort":0,"_child":[{"id":1194,"year":"2018","code":3,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1195,"year":"2018","code":4,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1196,"year":"2018","code":9,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1197,"year":"2018","code":10,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1198,"year":"2018","code":14,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1199,"year":"2018","code":15,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1200,"year":"2018","code":20,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1201,"year":"2018","code":25,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1202,"year":"2018","code":26,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1203,"year":"2018","code":31,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1204,"year":"2018","code":36,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1205,"year":"2018","code":37,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1206,"year":"2018","code":41,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1207,"year":"2018","code":42,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1208,"year":"2018","code":47,"attr":"蓝波","attr_id":8,"color":null,"color_id":0},{"id":1209,"year":"2018","code":48,"attr":"蓝波","attr_id":8,"color":null,"color_id":0}]},{"id":9,"title":"绿波","pid":1,"sort":0,"_child":[{"id":1210,"year":"2018","code":5,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1211,"year":"2018","code":6,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1212,"year":"2018","code":11,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1213,"year":"2018","code":16,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1214,"year":"2018","code":17,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1215,"year":"2018","code":21,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1216,"year":"2018","code":22,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1217,"year":"2018","code":27,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1218,"year":"2018","code":28,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1219,"year":"2018","code":32,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1220,"year":"2018","code":33,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1221,"year":"2018","code":38,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1222,"year":"2018","code":39,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1223,"year":"2018","code":43,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1224,"year":"2018","code":44,"attr":"绿波","attr_id":9,"color":null,"color_id":0},{"id":1225,"year":"2018","code":49,"attr":"绿波","attr_id":9,"color":null,"color_id":0}]}]
     */

    private int id;
    private String title;
    private int pid;
    private int sort;
    private List<ChildBeanX> _child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }



    public List<ChildBeanX> get_child() {
        return _child;
    }

    public void set_child(List<ChildBeanX> _child) {
        this._child = _child;
    }

    public static class ChildBeanX {
        /**
         * id : 7
         * title : 红波
         * pid : 1
         * sort : 0
         * _child : [{"id":1177,"year":"2018","code":1,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1178,"year":"2018","code":2,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1179,"year":"2018","code":7,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1180,"year":"2018","code":8,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1181,"year":"2018","code":12,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1182,"year":"2018","code":13,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1183,"year":"2018","code":18,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1184,"year":"2018","code":19,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1185,"year":"2018","code":23,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1186,"year":"2018","code":24,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1187,"year":"2018","code":29,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1188,"year":"2018","code":30,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1189,"year":"2018","code":34,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1190,"year":"2018","code":35,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1191,"year":"2018","code":40,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1192,"year":"2018","code":45,"attr":"红波","attr_id":7,"color":null,"color_id":0},{"id":1193,"year":"2018","code":46,"attr":"红波","attr_id":7,"color":null,"color_id":0}]
         */

        private int id;
        private String title;
        private int pid;
        private int sort;
        private List<ChildBean> _child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<ChildBean> get_child() {
            return _child;
        }

        public void set_child(List<ChildBean> _child) {
            this._child = _child;
        }

        public static class ChildBean {
            /**
             * id : 1177
             * year : 2018
             * code : 1
             * attr : 红波
             * attr_id : 7
             * color : null
             * color_id : 0
             */

            private int id;
            private String year;
            private int code;
            private String attr;
            private int attr_id;
            private Object color;
            private int color_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public int getAttr_id() {
                return attr_id;
            }

            public void setAttr_id(int attr_id) {
                this.attr_id = attr_id;
            }

            public Object getColor() {
                return color;
            }

            public void setColor(Object color) {
                this.color = color;
            }

            public int getColor_id() {
                return color_id;
            }

            public void setColor_id(int color_id) {
                this.color_id = color_id;
            }
        }
    }
}
