package Horarios;

import akka.actor.UntypedActor;

public class Medico extends UntypedActor {
    private static final long TIEMPO_EN_ATENDER_PACIENTE = 10000;
    
    public enum Mensaje{
        ATENDER_PACIENTE
    }
    
    private void redactarHC() throws InterruptedException {
        Thread.sleep(TIEMPO_EN_ATENDER_PACIENTE);
    }
   
    @Override
    public void onReceive(Object o) throws InterruptedException {
        System.out.println("[Medico] ha recibido mensaje de atender paciente: " + o);
        if (o == Mensaje.ATENDER_PACIENTE) {            
            System.out.println("[Medico] está atendiendo a paciente.");
            redactarHC();
            System.out.println("[Medico] ha terminado de atender al paciente.");
            getSender().tell(Enfermera.Mensaje.LLAMAR_OTRO_PACIENTE, getSelf());
        } else {
            unhandled(o);
        }
    }
}