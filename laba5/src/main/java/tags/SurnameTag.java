package tags;

import lombok.Data;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@Data
public class SurnameTag extends TagSupport {

    String attr;

    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if (attr != null)
            out.print("<p>Surname: <input name=\"surname\" value=\""+attr+"\"type=\"text\"/></p>");
        else
            out.print("<p>Surname: <input name=\"surname\" type=\"text\"/></p>");
        return SKIP_BODY;
    }
}
