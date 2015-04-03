namespace java node.thrift

typedef i32 int 

service QueryGenerator
{
		void regOKSuccess1 (1:string ip, 2: int port),
		void regOKSuccess2 (1:string ip1, 2: int port1, 3:string ip2, 4:int port2),
		void join(1:string ip, 2:int port),
		void leave(1:string ip, 2:int port),
		void fileSearch1(1:string fileName),
		void fileSearch2(1:string fileName, 2:string ip, 3:int port, 4:int id)

}