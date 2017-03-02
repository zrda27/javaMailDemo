<%--
  Created by IntelliJ IDEA.
  User: zrd
  Date: 2017/3/2
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JavaMail demo</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <div class="panel panel-default" style="margin-top: 30px;">
            <div class="panel-body">
                <b><strong>Login Info:</strong></b>
            </div>
        </div>
        <ul class="nav nav-tabs" style="margin-bottom: 30px;">
            <li role="presentation" class="active" id="configureLi"><a href="javascript:switchDiv('#configureLi', '#configureDiv');">configure</a></li>
            <li role="presentation" id="sendLi"><a href="javascript:switchDiv('#sendLi', '#sendDiv');">send Email</a></li>
            <li role="presentation" id="restrievesLi"><a href="javascript:switchDiv('#restrievesLi', '#restrievesDiv');">retrieves Email</a></li>
        </ul>
        <div id="configureDiv" class="divs">
            <form>
                <div class="form-group">
                    <label class="sr-only">Restrieves Email Server url</label>
                    <input type="text" class="form-control" placeholder="Enter Restrieves Email Server url" required
                        pattern="^(imap|imaps|pop3|pop3s)://" title="start with imap or imaps or pop3 or pop3s">
                    <span class="help-block">
                        e.g. google imap server address is <strong>imap(s)://imap.gmail.com</strong>,
                        pop3 server address is <strong>pop3(s)://pop:gmail.com</strong></span>
                </div>
                <div class="form-group">
                    <label class="sr-only">Send Email Server url</label>
                    <input type="text" class="form-control" placeholder="Enter Send Email Server url" required
                           pattern="^(smtp|smtps)://" title="start with smtp or smtps">
                    <span class="help-block">
                        e.g. google smtp server address is <strong>smtp(s)://smtp.gmail.com</strong>
                    </span>
                </div>
                <div class="form-group">
                    <label class="sr-only">Email Account</label>
                    <input type="email" class="form-control" placeholder="Enter Send Email Account" required>
                </div>
                <div class="form-group">
                    <label class="sr-only">Email Password</label>
                    <input type="password" class="form-control" placeholder="Enter Send Email Password" required>
                </div>
                <div class="form-group">
                    <input type="submit" class="btn btn-primary form-control" value="login">
                </div>
            </form>
        </div>
        <div id="sendDiv" class="divs" hidden>
            <div class="form-group">
                <label class="sr-only">recipients</label>
                <input type="text" class="form-control" placeholder="Enter recipientst, split with;" required>
            </div>
            <div class="form-group">
                <label class="sr-only">ccRecipients</label>
                <input type="text" class="form-control" placeholder="Enter ccRecipients, split with;" required>
            </div>
            <div class="form-group">
                <label class="sr-only">bccRecipients</label>
                <input type="text" class="form-control" placeholder="Enter bccRecipients, split with;" required>
            </div>
            <div class="form-group">
                <label class="sr-only">subject</label>
                <input type="text" class="form-control" placeholder="Enter subject" required>
            </div>
            <div class="form-group">
                <label>content</label>
                <textarea class="form-control" required>

                </textarea>
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-primary form-control" value="send">
            </div>
        </div>
        <div id="restrievesDiv" class="divs" hidden>
            <table class="table">
                <tr>
                    <th>sender</th>
                    <th>subject</th>
                    <th>detail</th>
                </tr>
                <tr>
                    <td>aa@gmail.com</td>
                    <td>subject</td>
                    <td><input type="button" class="btn btn-default" value="show detail"></td>
                </tr>
            </table>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script>
        function switchDiv(liId, divId){
            $('.divs').hide();
            $(divId).show();
            $(liId).parent().find("li").removeClass("active");
            $(liId).addClass("active");
        }
    </script>
</body>
</html>
