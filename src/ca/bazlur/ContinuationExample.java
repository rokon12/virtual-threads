//package ca.bazlur;
//
//import jdk.internal.vm.Continuation;
//import jdk.internal.vm.ContinuationScope;
//
////add this to VM Options on IntelliJ IDEA run configuration to run this example
////--add-exports=java.base/jdk.internal.vm=ALL-UNNAMED
//public class ContinuationExample {
//    public static void main(String[] args) throws InterruptedException {
//        var scope = new ContinuationScope("JConf Demo");
//
//        var continuation = new Continuation(scope, () -> {
//            System.out.println("C1");
//
//            Continuation.yield(scope);
//
//            System.out.println("C2");
//        });
//
//
//        System.out.println("Start here");
//        continuation.run();
//        System.out.println("Coming back");
//        continuation.run();
//        System.out.println("Finish");
//    }
//}
