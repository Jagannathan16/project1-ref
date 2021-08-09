<!DOCTYPE html>
<%@ page import="java.util.*,com.example.entity.History" %>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="transactionHistory.css">
        <title>Document</title>
    </head>

    <body>
        <% List<History> listOfHistory = (List<History>)request.getAttribute("history-list");
            %>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Account Number</th>
                        <th>Amount</th>
                        <th>Debit/Credit</th>
                        <th>balance</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(History history : listOfHistory){ %>
                        <tr>
                            <td><%=history.getDate()%></td>
                            <td><%=history.getToAccountNo()%></td>
                            <td><%=history.getAmount()%></td>
                            <td><%=history.getType()%></td>
                            <td><%=history.getBalance()%></td>
                        </tr>
                    <% } %>
                </tbody>

            </table>
        </div>
            

    </body>

    </html>