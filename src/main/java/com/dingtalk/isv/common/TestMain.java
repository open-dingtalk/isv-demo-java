package com.dingtalk.isv.common;

/**
 * Created by lifeng.zlf on 2016/4/11.
 */
public class TestMain {

    // ��ͨ�������ڲ��า��ThreadLocal��initialValue()������ָ����ʼֵ
    private static ThreadLocal<String> seqNum = new ThreadLocal<String>();

    // �ڻ�ȡ��һ������ֵ
    public String getNextNum() {
        if(null==seqNum.get()){
            seqNum.set("sdfsdf");
        }
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    private static class TestClient extends Thread {
        private TestMain sn;

        public TestClient(TestMain sn) {
            this.sn = sn;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                // ��ÿ���̴߳��3������ֵ
                System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                        + sn.getNextNum() + "]");
            }
        }
    }
    public static void main(String [] args){
        TestMain sn = new TestMain();
        // �� 3���̹߳���sn�����Բ������к�
        TestClient t1 = new TestClient(sn);
        TestClient t2 = new TestClient(sn);
        TestClient t3 = new TestClient(sn);
        t1.start();
        t2.start();
        t3.start();
    }
}
