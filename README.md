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