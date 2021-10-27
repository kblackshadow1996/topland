//package cn.topland.config;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
///**
// * 全局事务管理器
// * TODO  影响directus返回成果，全部事务屏蔽
// */
//@Aspect
//@Configuration
//public class TransactionAdviceConfig {
//
//    private static final String AOP_POINTCUT_EXPRESSION = "execution (* cn.topland.service..*Service.*(..))";
//
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
//    @Bean
//    public TransactionInterceptor txAdvice() {
//
//        DefaultTransactionAttribute txRequired = new DefaultTransactionAttribute();
//        txRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        DefaultTransactionAttribute txSupports = new DefaultTransactionAttribute();
//        txSupports.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
//        txSupports.setReadOnly(true);
//
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//        source.addTransactionalMethod("add*", txRequired);
//        source.addTransactionalMethod("create*", txRequired);
//        source.addTransactionalMethod("save*", txRequired);
//        source.addTransactionalMethod("update*", txRequired);
//        source.addTransactionalMethod("delete*", txRequired);
//        source.addTransactionalMethod("remove*", txRequired);
//        source.addTransactionalMethod("*", txSupports);
//        return new TransactionInterceptor(transactionManager, source);
//    }
//
//    @Bean
//    public Advisor txAdviceAdvisor() {
//
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
//        return new DefaultPointcutAdvisor(pointcut, txAdvice());
//    }
//}