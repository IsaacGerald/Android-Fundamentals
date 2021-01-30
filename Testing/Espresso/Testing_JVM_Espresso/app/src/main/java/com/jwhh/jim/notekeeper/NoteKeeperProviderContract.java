package com.jwhh.jim.notekeeper;

import android.net.Uri;
import android.provider.BaseColumns;

public final class NoteKeeperProviderContract {
    private NoteKeeperProviderContract(){}

    public static final String AUTHORITY = "com.jwhh.jim.notekeeper.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    protected interface coursesIdColumns{
        public static final String COLUMN_COURSE_ID = "course_id";
    }

    protected interface coursesColumns{

        public static final String COLUMN_COURSE_TITLE = "course_title";
    }
    protected interface notesColumns{
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_TEXT = "note_text";

    }

    public static final class Courses implements BaseColumns, coursesColumns, coursesIdColumns {
        public static final String PATH = "courses";
        //content://com.jwhh.jim.notekeeper.provider/courses
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);
    }
    public static final class Notes implements BaseColumns, notesColumns, coursesIdColumns, coursesColumns{
        public static final String PATH = "notes";
        //content://com.jwhh.jim.notekeeper.provider/notes
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);
        public static final String PATH_EXPANDED = "notes_expanded";
        public static final Uri CONTENT_EXPANDED_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH_EXPANDED);


    }
}

