package bean;

public interface process_scheduling_algorithm {
    public void round_cal();
    public pcb get_process_round();
    public int process_finish();
    public void display_round();
    public void set_state();
    public void cpu_round(pcb q);// 采用时间片轮转调度算法执行某一进程
    public pcb get_next(pcb k, pcb head);
}