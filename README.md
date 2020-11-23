<h2>Crypto Wallet</h2>

<h3>Terminology</h3>

<b><i>Wallet</i></b> - balance holder.

<b><i>Balance</i></b> - amount of money in the wallet.

<b><i>Balance History</i></b> - aggregated by time wallet's balance changes.

<b><i>Local Balance</i></b> - per thread in-memory buffered balance. Each thread has it's own balance which
is periodically flushed and reset. 

<b><i>Transfer</i></b> - a peace of wallet's balance changes.

<b><i>Deposit</i></b> - a process of transferring money to the wallet.

The application is intended to receive Bitcoins from a lot of people around the world. It must have
high throghput in order to support all income transfers. That's why the application has in-memory
buffered local balances which allow maintaining huge amount of income transfers.
At this moment the application is not supporting multi instance deploy mode.

Application supports two databases: H2 and Postgres. By default the application runs with H2 im-memory database.
H2 UI console is available by the link http://localhost:8080/api/v1/h2-console
In order to use Postgres run the application with the next environment variable: 
<code>spring.profiles.active=postgres</code>

The next environment variables are supported:

<code>WALLET_ID</code> - unique identifier of the default wallet. Must be UUID format. Default is <code>b1116a97-1e7c-484b-a111-89cc718c7772</code>

<code>APP_LOG_LEVEL</code> - log level for the application packages. Default is <code>ERROR</code>

<code>SPRING_LOG_LEVEL</code> - log level for the spring packages. Default is <code>ERROR</code>

<code>ROOT_LOG_LEVEL</code> - log level for the other packages. Default is <code>INFO</code>

The next environment variables are available in <code>postgres</code> profile:

<code>DB_SCHEMA</code> - defines the schema where all application's tables will be located. Default is <code>public</code>

<code>DB_URL</code> - jdbc database url. Default is <code>jdbc:postgresql://localhost:5432</code>

<code>DB_NAME</code> - database name. Default is <code>postgres</code>

<code>DB_USERNAME</code> - database username. Default is <code>postgres</code>

<code>DB_PASSWORD</code> - database password. Default is <code>postgres</code>

<h3>Run Application</h3>

1. Make sure that you have running Docker daemon on your machine and have internet access
2. Make sure your user is in <i>docker</i> group 
3. Make sure mvnw and run.sh are executable <code>sudo chmod +x \<filename\></code>
4. Run <code>run.sh</code>


