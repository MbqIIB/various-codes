#if($tr.extract($line.get(4),"cr.m.|bronz"))
	#set($prod ="autobronzant > soin beaut�"  )
	#end
	

    #if($tr.extract($line.get(4),"parfum|eau.toil.tt."))
	#set($prod ="parfum > parfum" )
	#end

    #if($tr.extract($line.get(4),"s.l.|bain"))
	#set($prod ="sels > hygi�ne et sant�"  )
	#end


    #if($tr.extract($line.get(4),"shamp."))
	#set($prod ="shampooing > hygi�ne et sant�" )
	#end


    #if($tr.extract($line.get(4),"g.l|d.m.llant"))
	#set($prod ="gel shampooing douche > hygi�ne et sant�" )
	#end
	
	$!prod