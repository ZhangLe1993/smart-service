# coding: utf-8
from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = "imoocd@163.com"
authorCode = "aA111111"
class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage")
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage")
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header("验证邮件", "utf-8")
        try:
            smtpObj = smtplib.SMTP("smtp.163.com")
            smtpObj.login(sender, authorCode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
            print("send email success")
            return True
        except smtplib.SMTPException as ex:
            print("send email failure")
            print("异常信息:" + ex)
            return False


if __name__ == '__main__':
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    transport = TSocket.TServerSocket("localhost","9090")
    tfactory = TTransport.TFramedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print("python thrift server start")
    server.serve()
    print("python thrift server exit")