import net.logstash.logback.encoder.LogstashEncoder

def logsDir = System.getenv("LOGS_DIR")
def date = timestamp("yyyy-MM-dd")

appender("FILE", RollingFileAppender) {
    file = "${logsDir}/crypto-wallet-${date}.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${logsDir}/archive/crypto-wallet-%d{yyyy-MM-dd}.zip"
    }
    encoder(LogstashEncoder) {
        customFields = """{"service" : "crypto-wallet"}"""
    }
}

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

root(INFO, ["FILE", "STDOUT"])