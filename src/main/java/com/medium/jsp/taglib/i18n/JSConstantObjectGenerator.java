package com.medium.jsp.taglib.i18n;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

public class JSConstantObjectGenerator extends TagSupport
{
   private static final long serialVersionUID = 1L;

   private String resourceBundleBaseName;

   private String jsObjectName;

   // called at start of tag
   public int doStartTag() throws JspException
   {
      String renderedJsObjectName = jsObjectName == null?"constants":jsObjectName;

      JspWriter out = pageContext.getOut();
      try
      {
         LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext, resourceBundleBaseName);

         ResourceBundle resourceBundle = locCtxt.getResourceBundle();

         out.write("<script type='text/javascript'>");
         if (resourceBundle != null)
         {
            Enumeration<String> keys = resourceBundle.getKeys();

            out.write("var " + renderedJsObjectName + " = {");
            while (keys.hasMoreElements())
            {
               String key = keys.nextElement();
               String value = resourceBundle.getString(key);

               String jsKey = removePeriodFromKey(key);
               String jsValue = StringEscapeUtils.escapeJavaScript(value);

               out.write(jsKey);
               out.write(":");
               out.write("'");
               out.write(jsValue);
               out.write("'");
               if (keys.hasMoreElements())
                  out.write(",");
            }
            out.write("};");
         }
         out.write("</script>");
      }
      catch (java.io.IOException e)
      {
         throw new JspException("IOException while writing to client: " + e);
      }

      return SKIP_BODY;
   }

   private String removePeriodFromKey(String key)
   {
      return key.replace(".", "");
   }

   // called at end of tag
   public int doEndTag()
   {
      return EVAL_PAGE;
   }

   public String getResourceBundleBaseName()
   {
      return resourceBundleBaseName;
   }

   public void setResourceBundleBaseName(String resourceBundleBaseName)
   {
      this.resourceBundleBaseName = resourceBundleBaseName;
   }

   public String getJsObjectName()
   {
      return jsObjectName;
   }

   public void setJsObjectName(String jsObjectName)
   {
      this.jsObjectName = jsObjectName;
   }

}
