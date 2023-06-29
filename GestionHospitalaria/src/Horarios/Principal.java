package Horarios;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Principal {
    public static ActorRef paciente1;
    public static ActorRef paciente2;
    public static ActorRef enfermera;
    public static ActorRef medico;
    public static ActorSystem actorSystem;
    
    private static final long TIEMPO_ESPERA = 1000;
    
    public static void main(String[] args) throws InterruptedException {
        actorSystem = ActorSystem.create("ActorSystem");        
        paciente1 = actorSystem.actorOf(Props.create(Paciente.class), "paciente1");
        paciente2 = actorSystem.actorOf(Props.create(Paciente.class), "paciente2");
        enfermera = actorSystem.actorOf(Props.create(Enfermera.class), "enfermera");
        medico = actorSystem.actorOf(Props.create(Medico.class), "medico");
        paciente1.tell(Paciente.Mensaje.ESCUCHA_NOMBRE, ActorRef.noSender());
        Thread.sleep(TIEMPO_ESPERA);
        paciente2.tell(Paciente.Mensaje.ESCUCHA_NOMBRE, ActorRef.noSender());
    } 
}