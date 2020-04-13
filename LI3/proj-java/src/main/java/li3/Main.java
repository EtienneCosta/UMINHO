package li3;

import common.MyLog;
import common.Pair;
import engine.PostInexistenteException;
import engine.StackOverflowController;
import engine.StackOverflowView;
import engine.TCDExample;
import engine.UserInexistenteException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*
            LOG CONFIGURATION
         */
        MyLog log = new MyLog("results");
        MyLog logtime = new MyLog("times");
        /* -------------------------------------------------------------------------------------------*/

        long before, after;
        TADCommunity qe = new TCDExample();
        StackOverflowController controller = new StackOverflowController();
        StackOverflowView view = new StackOverflowView();

        /*
            LOAD PHASE
         */
        try {
            before = System.currentTimeMillis();
            qe.load(args[0]);
            after = System.currentTimeMillis();
            logtime.writeLog("LOAD -> " + (after - before) + " ms");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Deve passar o caminho do dump como argumento.");
        }

        controller.setView(view);
        controller.setModel(qe);
        controller.startFlow();

        /*
           Query 1
         */
        before = System.currentTimeMillis();
        try {
            Pair<String, String> q1 = qe.infoFromPost(801049);
            after = System.currentTimeMillis();
            logtime.writeLog("Query 1: -> " + (after - before) + " ms");
            log.writeLog("Query1 -> " + q1);
        } catch (PostInexistenteException e) {
            System.out.println("O ID de Post fornecido " + e.getMessage() + " não existe!!!");
        }

        /*
           Query 2
         */
        before = System.currentTimeMillis();
        List<Long> q2 = qe.topMostActive(10);
        after = System.currentTimeMillis();
        logtime.writeLog("Query 2 -> " + (after - before) + " ms");
        log.writeLog("Query 2 -> " + q2);

        /*
           Query 3
         */
        before = System.currentTimeMillis();
        Pair<Long, Long> q3 = qe.totalPosts(LocalDate.of(2016, Month.JULY, 1),
                LocalDate.of(2016, Month.JULY, 31));
        after = System.currentTimeMillis();
        logtime.writeLog("Query 3 -> " + (after - before) + " ms");
        log.writeLog("Query 3 -> " + q3);

        /*
           Query 4
         */
        before = System.currentTimeMillis();
        List<Long> query4 = qe.questionsWithTag("package-management", LocalDate.of(2013, Month.MARCH, 1),
                LocalDate.of(2013, Month.MARCH, 31));
        after = System.currentTimeMillis();
        logtime.writeLog("Query 4 -> " + (after - before) + " ms");
        log.writeLog("Query 4 -> " + query4);

        /*
           Query 5
         */
        before = System.currentTimeMillis();
        try {
            Pair<String, List<Long>> q5 = qe.getUserInfo(15811);
            after = System.currentTimeMillis();
            logtime.writeLog("Query 5 -> " + (after - before) + " ms");
            log.writeLog("Query 5 -> " + q5);
        } catch (UserInexistenteException e) {
            System.out.println("O ID de utilizador fornecido " + e.getMessage() + " não existe!!!");
        }

        /*
           Query 6
         */
        before = System.currentTimeMillis();
        List<Long> q6 = qe.mostVotedAnswers(5, LocalDate.of(2015, Month.NOVEMBER, 1),
                LocalDate.of(2015, Month.NOVEMBER, 30));
        after = System.currentTimeMillis();
        logtime.writeLog("Query6 -> " + (after - before) + " ms");
        log.writeLog("Query6 -> " + q6);

        /*
           Query 7
         */
        before = System.currentTimeMillis();
        List<Long> q7 = qe.mostAnsweredQuestions(10, LocalDate.of(2014, Month.AUGUST, 1),
                LocalDate.of(2014, Month.AUGUST, 11));
        after = System.currentTimeMillis();
        logtime.writeLog("Query 7 -> " + (after - before) + " ms");
        log.writeLog("Query 7 -> " + q7);

        /*
           Query 8
         */
        before = System.currentTimeMillis();
        List<Long> q8 = qe.containsWord(10, "kde");
        after = System.currentTimeMillis();
        logtime.writeLog("Query 8 -> " + (after - before) + " ms");
        log.writeLog("Query 8 -> " + q8);

        /*
           Query 9
         */
        before = System.currentTimeMillis();
        try {
            List<Long> q9 = qe.bothParticipated(10, 87, 5691);
            after = System.currentTimeMillis();
            logtime.writeLog("Query9 -> " + (after - before) + " ms");
            log.writeLog("Query 9 -> " + q9);
        } catch (UserInexistenteException e) {
            System.out.println("O ID de utilizador fornecido " + e.getMessage() + " não existe!!!");
        }

        /*
           Query 10
         */
        before = System.currentTimeMillis();
        try {
            long q10 = qe.betterAnswer(30334);
            after = System.currentTimeMillis();
            logtime.writeLog("Query 10 -> " + (after - before) + " ms");
            log.writeLog("Query 10 -> " + q10);
        } catch (PostInexistenteException e) {
            System.out.println("O ID de Post fornecido " + e.getMessage() + " não existe!!!");
        }

        /*
            Query 11
         */
        before = System.currentTimeMillis();
        List<Long> q11 = qe.mostUsedBestRep(5, LocalDate.of(2013, Month.NOVEMBER, 01),
                LocalDate.of(2013, Month.NOVEMBER, 30));
        after = System.currentTimeMillis();
        logtime.writeLog("Query 11 -> " + (after - before) + " ms");
        log.writeLog("Query 11 -> " + q11);

        /*
            CLEAN PHASE
         */
        before = System.currentTimeMillis();
        qe.clear();
        after = System.currentTimeMillis();
        logtime.writeLog("CLEAN -> " + (after - before) + " ms");

    }

}
