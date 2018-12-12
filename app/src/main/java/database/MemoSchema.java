package database;

public class MemoSchema {
    public static final class Table{
        public static final String NAME = "MemoTable";

        public static final class Cols{
            public static final String UUID = "id";
            public static final String TITLE = "title";
            public static final String TEXT = "text";
            public static final String DATE = "date";
        }
    }
}
