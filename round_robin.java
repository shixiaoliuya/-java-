package bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class round_robin implements process_scheduling_algorithm,Cloneable{
    public static final int P_NUM = 5;
    public static final int P_TIME = 50;
    public static List list = new LinkedList<pcb>();
    //public int position = 1;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void round_cal() {
        pcb p,r;
        p = get_process_round();
        //System.out.println(p.getName()+"--------");
        //display_round(p);
        int cpu = 0;
        r = (pcb)p.clone();//r = p;
        while (process_finish() == 0){
            cpu += 2;
            cpu_round(r);
            r = (pcb) get_next(r,p).clone();//r = get_next(r,p);
            // System.out.println(r.getName() + "------");
            System.out.println("cpu  " + cpu);
            display_round();
            set_state();
        }
    }

    @Override
    public pcb get_process_round() {
        pcb q = new pcb();
        pcb t = new pcb();
        pcb p = new pcb();
        int i = 0;
        System.out.println("input name and time");
        while(i < P_NUM){
            q.setName(cin.next());
            q.setNeedtime(cin.nextInt());
            q.setCputime(0);
            q.setRound(0);
            q.setCount(0);
            q.setProcess(state.ready);
            q.setNext(null);
            if(i == 0){
                // p = q;
                p = (pcb) q.clone();
                //System.out.println(p.needtime + "---------");
                //t = q;
                t = (pcb) q.clone();
                list.add(t);
            } else {
                //t.next = q; //创建就绪进程队列
                t.setNext(q);
                //t = q;
                t = (pcb) q.clone();
                list.add(t);
            }
            i++;
        }
//        for(int x=0;x<list.size();x++) {
//        	pcb temp = (pcb) list.get(x);
//        	System.out.println(temp.getName());
//        }
        //System.out.println(p.getName() + "------");
        return p;
    } //输入模拟测试的进程名和执行所需时间

    @Override
    public int process_finish() {
        //System.out.println(q.needtime + "------------");
        int bl = 1,i = 0;
        while(bl!=0 && i < list.size()){
            pcb q = (pcb) list.get(i);
            if(bl != 0 && q.getNeedtime() == 0)bl = 1;
            else bl = 0;
            i++;
            //q = q.next;
            // q = q.getNext();
            // System.out.println(q.getName());
        }
        return bl;
    }

    @Override
    public void display_round() {
        System.out.println("NAME         CPUTIME         NEEDTIME         COUNT         ROUND         STATE");
        for(int i=0;i<list.size();i++){
            pcb p = (pcb) list.get(i);
            System.out.print(p.getName() + "             ");
            System.out.print(p.getCputime() + "               ");
            System.out.print(p.getNeedtime() + "               ");
            System.out.print(p.getCount() + "              ");
            System.out.print(p.getRound() + "           ");
            switch (p.getProcess()){
                case ready:
                    System.out.println("ready");
                    break;
                case execute:
                    System.out.println("execute");
                    break;
                case finish:
                    System.out.println("finish");
                    break;
            }
            //p = p.getNext();
        }
    }

    @Override
    public void set_state() {
        for(int i=0;i<list.size();i++){
            pcb p = (pcb) list.get(i);
            if(p.getNeedtime() == 0) p.setProcess(state.finish);
            if(p.getProcess() == state.execute) p.setProcess(state.ready);
            //p = p.getNext();
        }
    }

    @Override
    public void cpu_round(pcb q) {
        q.setCputime(q.getCputime() + 2);//q.cputime += 2;
        q.setNeedtime(q.getNeedtime() - 2);//q.needtime -= 2;
        if (q.getNeedtime() < 0) q.setNeedtime(0);
        q.setCount(q.getCount() + 1);//q.count++;
        q.setRound(q.getRound() + 1);//q.round++;
        q.setProcess(state.execute);//q.process = state.execute;
        for(int i=0;i<list.size();i++) {
            pcb temp = (pcb) list.get(i);
            if(q.getName() == temp.getName()) {
                list.remove(i);
                list.add(i, q);
            }
        }
    }//采用时间片轮转调度算法执行某一进程

    @Override
    public pcb get_next(pcb k, pcb head) {
        //System.out.println(k.getName() + " " + head.getName() + "------");
        pcb t;
        t = (pcb) k.clone();//t = k;
        int opsition = 0;
        for(int i=0;i<list.size();i++) {//从列表中查找k的位置
            pcb temp = (pcb) list.get(i);
            if(t.getName() == temp.getName()) {
                opsition = i;
                break;
            }
        }
        do {
            //t = t.getNext();//t = t.next;
            if(opsition+1 < list.size()) {
                t = (pcb) list.get(++opsition);
                //System.out.println(t.getName() +"-----------");
                //break;
            }
            else{
                opsition++;
                break;
            }
        }while(t != null && t.getProcess()== state.finish && opsition < list.size());
        if(opsition == list.size()) {
            t = (pcb) head.clone();//t = head;
            //System.out.println(t.getName() + " -------------");
            //while (t.getNext() != k && t.getProcess() == state.finish) t = t.getNext();
            for(int i = 0; i < list.size(); i++){
                pcb temp = (pcb) list.get(i);
                if(temp.getName() == t.getName()){
                    opsition = i;
                    break;
                }
            }
            do {
                if(opsition+1 < list.size()) t = (pcb) list.get(++opsition);
                //opsition++;
            }while(t != null && t.getNext() != k && t.getProcess() == state.finish && opsition < list.size());
        }
        return t;
    }

    public static void main(String[] args) {
        round_robin test = new round_robin();
        test.round_cal();
    }
    public Scanner cin = new Scanner(System.in);
}
/*测试样例一
a1 2
a2 3
a3 4
a4 5
a5 6
测试样例二
a1 2
a2 3
a3 4
a4 2
a5 4
 */
