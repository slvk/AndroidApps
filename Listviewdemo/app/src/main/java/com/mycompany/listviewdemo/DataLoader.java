package com.mycompany.listviewdemo;

/**
 * Created by VIanoshchuk on 19.03.2015.
 */
public  class DataLoader {
    static public void LoadData(DatabaseAdapter myDb){
        if (myDb != null) {
            myDb.deleteAllData();

            long SubjId, QuestId;
            SubjId = myDb.insertSubject("Терапія");
            QuestId = myDb.insertQuestion(
                    "При дослiдженi кровi хворого виявлено значне збiльшення активностi МВ-форм КФК (креатинфосфокiнази) та ЛДГ-1. Яку патологiю можна  припустити?",
                    SubjId, 1);

            myDb.insertAnswer(QuestId, "Iнфаркт мiокарда", 1);
            myDb.insertAnswer(QuestId, "Гепатит", 0);
            myDb.insertAnswer(QuestId, "Ревматизм", 0);
            myDb.insertAnswer(QuestId, "Панкреатит", 0);
            myDb.insertAnswer(QuestId, "Холецистит", 0);

            QuestId = myDb.insertQuestion(
                    "У спортсмена внаслiдок довiльної затримки дихання на 40 секунд зросли частота серцевих скорочень та системний артерiальний тиск. Реалiзацiя яких механiзмiв регуляцiї зумовлює змiни показникiв?",
                    SubjId, 2);

            myDb.insertAnswer(QuestId, "Безумовнi симпатичнi рефлекси", 1);
            myDb.insertAnswer(QuestId, "Безумовнi парасимпатичнi рефлекси", 0);
            myDb.insertAnswer(QuestId, "Умовнi симпатичнi рефлекси", 0);
            myDb.insertAnswer(QuestId, "Умовнi парасимпатичнi рефлекси", 0);
            //myDb.insertAnswer(QuestId, "-", 0); // todo: find way to handle 4-answer questions

            SubjId = myDb.insertSubject("Хірургія");
            QuestId = myDb.insertQuestion(
                    "Пiсля загоєння рани на її мiсцi утворився рубець. Яка речовина є основним компонентом цього рiзновиду сполучної тканини?",
                    SubjId, 1);

            myDb.insertAnswer(QuestId, "Колаген", 1);
            myDb.insertAnswer(QuestId, "Еластин", 0);
            myDb.insertAnswer(QuestId, "Гiалуронова кислота", 0);
            myDb.insertAnswer(QuestId, "Хондроiтин-сульфат", 0);
            myDb.insertAnswer(QuestId, "Кератансульфат", 0);

            SubjId = myDb.insertSubject("Травматологія");
            QuestId = myDb.insertQuestion(
                    "В результатi травми пошкоджений спинний мозок (з повним розривом) на рiвнi першого шийного хребця.Що вiдбудеться з диханням?",
                    SubjId, 1);
            myDb.insertAnswer(QuestId, "Припиняється", 1);
            myDb.insertAnswer(QuestId, "Не змiнюється", 0);
            myDb.insertAnswer(QuestId, "Зростає частота", 0);
            myDb.insertAnswer(QuestId, "Зростає глибина", 0);
            myDb.insertAnswer(QuestId, "Зменшується частота", 0);


            SubjId = myDb.insertSubject("Інфекційні хвороби");
            QuestId = myDb.insertQuestion(
                    "При мiкроскопiї мiкропрепарату з видiлень хворої хронiчним кольповагiнiтом лiкар виявив округлої форми та елiпсоподiбнi клiтини, що брунькуються, розмiром 3-6 мкм.Про збудника якої грибкової хвороби може йти мова в даному випадку?",
                    SubjId, 1);
            myDb.insertAnswer(QuestId, "Кандидоз", 1);
            myDb.insertAnswer(QuestId, "Кокцидiоз", 0);
            myDb.insertAnswer(QuestId, "Епiдермофiтiя", 0);
            myDb.insertAnswer(QuestId, "Мiкроспорiя", 0);
            myDb.insertAnswer(QuestId, "Криптококоз", 0);

            QuestId = myDb.insertQuestion(
                    "До лiкарнi надiйшла дитина з дiагнозом \"стафiлококовий сепсис\". На яке живильне середовище потрiбно посiяти кров хворого з метою видiлення збудника?",
                    SubjId, 2);
            myDb.insertAnswer(QuestId, "Цукрово-пептонний бульйон", 1);
            myDb.insertAnswer(QuestId, "М’ясо-пептонний агар", 0);
            myDb.insertAnswer(QuestId, "Середовище Плоскiрьова", 0);
            myDb.insertAnswer(QuestId, "Середовище Бучiна", 0);
            myDb.insertAnswer(QuestId, "Жовчно-сольовий агар", 0);

            SubjId = myDb.insertSubject("Фармакологія");

            QuestId = myDb.insertQuestion("При обстеженнi чоловiка 40-ка рокiв було встановлено дiагноз: гiпохромна анемiя. Який препарат треба призначити для лiкування?",
                    SubjId, 1);
            myDb.insertAnswer(QuestId, "Ферковен", 1);
            myDb.insertAnswer(QuestId, "Цiанокобаламiн", 0);
            myDb.insertAnswer(QuestId, "Пентоксил", 0);
            myDb.insertAnswer(QuestId, "Гепарин", 0);
            myDb.insertAnswer(QuestId, "Вiкасол", 0);
        }
    }
}
