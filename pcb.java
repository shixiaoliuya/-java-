package bean;

public class pcb implements Cloneable{
    private String name;//进程名
    private int cputime;//CPU 运行时间
    private int needtime;//运行所需的时间
    private int count;//执行次数
    private int round;//时间片轮转轮次
    private state process;//进程状态
    private pcb next;

    public Object clone() {
        pcb person = null;
        try {
            person = (pcb)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCputime(int cputime) {
        this.cputime = cputime;
    }
    public int getCputime(){
        return cputime;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount(){
        return count;
    }

    public void setNeedtime(int needtime){
        this.needtime = needtime;
    }
    public int getNeedtime(){
        return needtime;
    }

    public void setRound(int round) {
        this.round = round;
    }
    public int getRound(){
        return round;
    }

    public void setProcess(state process) {
        this.process = process;
    }
    public state getProcess(){
        return process;
    }

    public void setNext(pcb p){
        if(p!=null)next = (pcb) p.clone();
        else next = null;
    }
    public pcb getNext(){
        return next;
    }
}

enum state{
    ready,
    execute,
    block,
    finish
};// 定义进程状态
