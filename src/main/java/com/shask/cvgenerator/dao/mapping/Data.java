package com.shask.cvgenerator.dao.mapping;

public class Data {
    //TODO refactor ( see to delete that class )
        private PersonWrapper data;

        public Data() {
        }

        public PersonWrapper getData() {
            return data;
        }

        public void setData(PersonWrapper data) {
            this.data = data;
        }

        public Data(PersonWrapper data) {
            this.data = data;
        }
    }