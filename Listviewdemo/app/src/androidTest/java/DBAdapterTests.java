import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.mycompany.listviewdemo.DatabaseAdapter;

/**
 * Created by VIanoshchuk on 17.03.2015.
 */

public class DBAdapterTests  extends AndroidTestCase {
    private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseAdapter mMyAdapter;

    @Override
    protected void setUp() throws Exception {
        System.out.println("setUp call");
        super.setUp();

        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);

        mMyAdapter = new DatabaseAdapter(context);
        mMyAdapter.open();

    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("tearDown call");
        super.tearDown();

        mMyAdapter.close();
        System.out.println("DB closed");
        mMyAdapter = null;
    }

    public void testPreConditions() {
        System.out.println("testPreConditions");
        assertNotNull(mMyAdapter);
    }

    public void testInsertAndGetData(){
/*        long SubjId = mMyAdapter.insertSubject("test subject2");
        SubjId = mMyAdapter.insertSubject("test subject3");
        SubjId = mMyAdapter.insertSubject("test subject1");

        Cursor c = mMyAdapter.getAllRowsSubjects();
        assertEquals(3, c.getCount()); // check that 3 subjs were inserted

        long QuestId;
        QuestId = mMyAdapter.insertQuestion("test question", SubjId, 1);
        assertNotNull(QuestId);

        mMyAdapter.insertAnswer(QuestId, "test answer1", 0);
        mMyAdapter.insertAnswer(QuestId, "test answer2", 1);
        mMyAdapter.insertAnswer(QuestId, "test answer3", 0);
        mMyAdapter.insertAnswer(QuestId, "test answer4", 0);
        mMyAdapter.insertAnswer(QuestId, "test answer5", 0);

        QuestionWithAnswer qa = mMyAdapter.getQuestionBySN((int)SubjId, 1);
        assertNotNull(qa);
        assertNotNull(qa.Question);
        assertEquals(qa.Answers.length, 5);
       // assertEquals(qa.CorrectAnswerNumber, 1); // db returns correct answer starting from 0

        QuestId = mMyAdapter.insertQuestion("test question1", SubjId, 2);
        QuestId = mMyAdapter.insertQuestion("test question2", SubjId, 3);
        int cntQuests = mMyAdapter.getCountQuestionsInSubject(SubjId);
        assertEquals(3, cntQuests);  */
    }

}
