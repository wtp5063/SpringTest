package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.common.security.LoginUser;
import com.example.demo.model.CustomerEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerInformationControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;

    @MockBean
    private SecurityContextHolder securityContext;

    @MockBean
    private SecurityContext context;

    @MockBean
    private Authentication authentication;

    @MockBean
    private Model model;

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockHttpSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    @WithMockUser
    void testInformation() throws Exception
    {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class))
        {
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            CustomerEntity expectedCustomer = new CustomerEntity();
            expectedCustomer.setRole("ROLE_SEEKER");
            expectedCustomer.setEmail("mieko@example.com");
            expectedCustomer.setPassword("1111aaaa");
            when(context.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(new LoginUser(expectedCustomer));
            new CustomerInformationController().information(new CustomerEntity(), model);

            HttpServletRequest request;
            request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

            mockMvc.perform(MockMvcRequestBuilders.get("/customer_information").session(mockHttpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("layout"))
            .andExpect(model().attribute("customer", expectedCustomer))
            .andExpect(model().attribute("title", "プロフィール"))
            .andExpect(model().attribute("main", "customerInformation::main"));
        }
    }

    /*
     * java.lang.NullPointerException
    at org.springframework.security.web.context.HttpSessionSecurityContextRepository$SaveToSessionResponseWrapper.<init>(HttpSessionSecurityContextRepository.java:316)
    at org.springframework.security.web.context.HttpSessionSecurityContextRepository.loadContext(HttpSessionSecurityContextRepository.java:126)
    at org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors$SecurityContextRequestPostProcessorSupport$TestSecurityContextRepository.loadContext(SecurityMockMvcRequestPostProcessors.java:760)
    at org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors$SecurityContextRequestPostProcessorSupport.save(SecurityMockMvcRequestPostProcessors.java:733)
    at org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors$TestSecurityContextHolderPostProcessor.postProcessRequest(SecurityMockMvcRequestPostProcessors.java:805)
    at org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.postProcessRequest(MockHttpServletRequestBuilder.java:831)
    at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:173)
    at com.example.demo.controller.CustomerInformationControllerTest.testInformation(CustomerInformationControllerTest.java:59)
    at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.base/java.lang.reflect.Method.invoke(Method.java:566)
    at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:688)
    at org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)
    at org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131)
    at org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149)
    at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140)
    at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84)
    at org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)
    at org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)
    at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)
    at org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)
    at org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)
    at org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)
    at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)
    at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)
    at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:210)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:206)
    at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:131)
    at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:65)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)
    at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)
    at java.base/java.util.ArrayList.forEach(ArrayList.java:1541)
    at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:143)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)
    at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)
    at java.base/java.util.ArrayList.forEach(ArrayList.java:1541)
    at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:143)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)
    at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)
    at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)
    at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)
    at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32)
    at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)
    at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51)
    at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:108)
    at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:88)
    at org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:54)
    at org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:67)
    at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:52)
    at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:96)
    at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:84)
    at org.eclipse.jdt.internal.junit5.runner.JUnit5TestReference.run(JUnit5TestReference.java:98)
    at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:41)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:542)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:770)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:464)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)


     */
}
