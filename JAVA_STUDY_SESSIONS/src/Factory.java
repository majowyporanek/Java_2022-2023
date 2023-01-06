//interface Sleeper {
//    public static void sleep(long howLong) {
//        try {
//            Thread.sleep(howLong);
//        } catch (InterruptedException e) {
//        }
//    }
//}
//
//class TeamOfWorkers implements Sleeper {
//    private boolean flag = true;
//    private int counter;
//
//    class Worker implements Runnable {
//        public void run() {
//            while (flag) {
//                counter++;
//            }
//        }
//    }
//
//    public TeamOfWorkers(int workers) {
//        for (int i = 0; i < workers; i++) {
//            Thread th = new Thread(new Worker());
//            th.setDaemon(true);
//            th.start();
//            Sleeper.sleep(2);
//        }
//    }
//
//    public void suspendTest() {
//        System.out.println("Stan flagi: " + flag);
//        flag = false;
//        System.out.println("Stan flagi: " + flag);
//        while (true) {
//            System.out.println("Stan flagi: " + flag);
//            System.out.println("Ile wynosi counter " + counter);
//            Sleeper.sleep(100);
//        }
//    }
//
//    public static void main(String[] args) {
//        TeamOfWorkers team = new TeamOfWorkers(10);
//        Sleeper.sleep(125);
//        team.suspendTest();
//    }
//}