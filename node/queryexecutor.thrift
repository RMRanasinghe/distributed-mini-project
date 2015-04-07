namespace java node.thrift

typedef i32 int 

service QueryExecutor
{
		void regOKSuccess1 (1:string ip, 2: int port),
		void regOKSuccess2 (1:string ip1, 2: int port1, 3:string ip2, 4:int port2),
		void join(1:string ip, 2:int port),
		void leave(1:string ip, 2:int port),
		oneway void fileSearch(1:string fileName, 2:string ip, 3:int port, 4:int id, 5:int hops)
		oneway void fileFound(1:list<string> fileList, 2:string searchIp, 3:int searchPort,4:string foundIp,5:int foundPort, 6:int id, 7:int hops)
}
