<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Madhukar
  Date: 23-11-2023
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2, h3 {
            color: #5D6975;
            font-size: 2.4em;
            text-align: center;
            margin-bottom: 30px;
        }
        h3 {
            font-size: 1.8em;
            margin-top: 40px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #e1e1e1;
            padding: 12px 15px;
            text-align: left;
        }
        th {
            background-color: #f7f7f7;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        p {
            font-size: 1.2em;
            line-height: 1.6em;
            text-align: center;
        }
        .logs-container {
            margin-top: 20px;
        }

        .log-entry {
            background-color: #fafafa;
            border: 1px solid #eaeaea;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 4px;
        }

        .log-entry p {
            margin: 5px 0;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Dashboard</h2>

    <h3>Top Phone Models</h3>
    <table>
        <tr>
            <th>Phone Model</th>
            <th>Count</th>
        </tr>
        <%
            List<Map<String, Object>> topPhoneModels = (List<Map<String, Object>>) request.getAttribute("topPhoneModels");
            for (Map<String, Object> model : topPhoneModels) {
                if (model.get("phoneModel") != null) {
        %>
        <tr>
            <td><%= model.get("phoneModel") %></td>
            <td><%= model.get("count") %></td>
        </tr>
        <%
            }
            }
        %>
    </table>

    <h3>Most Frequent Joke Types</h3>
    <table>
        <tr>
            <th>Joke Type</th>
            <th>Count</th>
        </tr>
        <%
            List<Map<String, Object>> jokeTypes = (List<Map<String, Object>>) request.getAttribute("getMostFrequentJokeTypes");
            for (Map<String, Object> type : jokeTypes) {
                if (type.get("jokeType") != null) {
        %>
        <tr>
            <td><%= type.get("jokeType") %></td>
            <td><%= type.get("count") %></td>
        </tr>
        <%
            }
            }
        %>
    </table>

    <h3>Average API Response Time</h3>
    <p>
        <%
            List<Map<String, Object>> responseTimes = (List<Map<String, Object>>) request.getAttribute("responseTimes");
            if (!responseTimes.isEmpty()) {
                Map<String, Object> avgTime = responseTimes.get(0);
        %>
        Average Response Time: <%= avgTime.get("avgResponseTime") %> ms
        <%
            }
        %>
    </p>


</div>
<h3>Logs</h3>
<div class="logs-container">
    <%
        List<Map<String, Object>> logs = (List<Map<String, Object>>) request.getAttribute("logs");
        for (Map<String, Object> logEntry : logs) {
    %>
    <div class="log-entry">
        <p>Request Time: <%= logEntry.get("requestReceivedTime") %></p>
        <p>API Request Start: <%= logEntry.get("apiRequestStart") %></p>
        <p>API Request End: <%= logEntry.get("apiRequestEnd") %></p>
        <p>Joke Response: <%= logEntry.get("jokeType") %></p>
        <p>Joke Response: <%= logEntry.get("jokeResponse") %></p>
        <p>Response Time: <%= logEntry.get("responseTime") %></p>
        <p>Phone Model: <%= logEntry.get("phoneModel") != null ? logEntry.get("phoneModel") : "Unknown" %></p>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
