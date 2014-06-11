package com.medium.jsp.taglib.i18n;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class JSConstantObjectGeneratorTest
{
   private PageContext mockPageContext;
   private ServletContext mockServletContext;
   private JspWriter mockJSPWriter;

   @Before
   public void setupMocks()
   {
      mockPageContext = Mockito.mock(PageContext.class);
      mockServletContext = Mockito.mock(ServletContext.class);
      mockJSPWriter = Mockito.mock(JspWriter.class);

      Mockito.when(mockPageContext.getServletContext()).thenReturn(mockServletContext);
      Mockito.when(mockPageContext.getOut()).thenReturn(mockJSPWriter);
      Mockito.when(mockPageContext.getResponse()).thenReturn(Mockito.mock(ServletResponse.class));
   }

   @Test
   public void testGenerateJSObject() throws JspException, IOException
   {
      Mockito.when(mockPageContext.getAttribute(Mockito.anyString(), Mockito.anyInt())).thenReturn(Locale.ENGLISH);

      final StringBuffer sb = new StringBuffer();

      Mockito.doAnswer(new StubJSPWriterOut(sb)).when(mockJSPWriter).write(Mockito.anyString());

      JSConstantObjectGenerator jsConstantObjectGenerator = new JSConstantObjectGenerator();
      jsConstantObjectGenerator.setPageContext(mockPageContext);

      jsConstantObjectGenerator.setResourceBundleBaseName("com.medium.jsp.taglib.i18n.JSConstantObjectGeneratorTest");

      jsConstantObjectGenerator.doStartTag();

      Assert.assertEquals("<script type='text/javascript'>var constants = {testmessage:'test'};</script>", sb.toString());
   }

   @Test
   public void testGenerateJSObjectWithQuote() throws JspException, IOException
   {
      Mockito.when(mockPageContext.getAttribute(Mockito.anyString(), Mockito.anyInt())).thenReturn(Locale.getDefault());

      final StringBuffer sb = new StringBuffer();

      Mockito.doAnswer(new StubJSPWriterOut(sb)).when(mockJSPWriter).write(Mockito.anyString());

      JSConstantObjectGenerator jsConstantObjectGenerator = new JSConstantObjectGenerator();
      jsConstantObjectGenerator.setPageContext(mockPageContext);

      jsConstantObjectGenerator.setResourceBundleBaseName("com.medium.jsp.taglib.i18n.JSConstantObjectGeneratorTestWithQuote");

      jsConstantObjectGenerator.doStartTag();

      Assert.assertEquals("<script type='text/javascript'>var constants = {testmessage:'t\\\'es\\\"t'};</script>", sb.toString());
   }

   @Test
   public void testGenerateJSObjectWithZhTW() throws JspException, IOException
   {
      Mockito.when(mockPageContext.getAttribute(Mockito.anyString(), Mockito.anyInt())).thenReturn(Locale.TRADITIONAL_CHINESE);

      final StringBuffer sb = new StringBuffer();

      Mockito.doAnswer(new StubJSPWriterOut(sb)).when(mockJSPWriter).write(Mockito.anyString());

      JSConstantObjectGenerator jsConstantObjectGenerator = new JSConstantObjectGenerator();
      jsConstantObjectGenerator.setPageContext(mockPageContext);

      jsConstantObjectGenerator.setResourceBundleBaseName("com.medium.jsp.taglib.i18n.JSConstantObjectGeneratorTest");

      jsConstantObjectGenerator.doStartTag();

      Assert.assertEquals("<script type='text/javascript'>var constants = {testmessage:'\\u6E2C\\u8A66'};</script>", sb.toString());
   }

   private final class StubJSPWriterOut implements Answer<Void>
   {
      private final StringBuffer sb;

      private StubJSPWriterOut(StringBuffer sb)
      {
         this.sb = sb;
      }

      public Void answer(InvocationOnMock invocation)
      {
         String strParam = (String) invocation.getArguments()[0];
         sb.append(strParam);
         return null;
      }
   }
}
