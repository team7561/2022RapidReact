even = int(input("is serial number even? "))
batteries = int(input("how many batteries? "))
parallel = int(input("is there a parallel? "))
vowel = input("is there a vowel in the serial? ")
FRK = int(input("is the FRK? "))
CAR = int(input("is there CAR? "))
strikes = int(0) 

while True:
	moduleType = input("Module Name: ")
	if moduleType == "w" or moduleType == "wires":
		wires = input("Enter colours: ")
		length = len(wires)
		if length == 3:
			if "r" not in wires:
				print("Second wire")
			elif wires[-1] == "w":
				print("Last wire")
			elif wires.count("b") >= 2:
				print("Cut last blue")
			else:
				print("Last wire")
		elif length == 4:
			if wires.count("r") >= 2 and even != "1":
				print("Cut last red")
			elif wires[-1] == "y" and "r" not in wires:
				print("Cut first")
			elif wires.count("b") == 1:
				print("Cut first")
			elif wires.count("y") >= 2:
				print("Cut last")
			else:
				print("Cut second")
		elif length == 5:
			if wires[-1] == "B" and even != "1":
				print("Cut fourth")
			elif wires.count("r") == 1 and wires.count ("y") >= 2:
				print("Cut first")
			elif "B" not in wires:
				print("Cut second")
			else:
				print("Cut first")
		else:
			if "y" not in wires and even != "1":
				print("Cut third")
			elif wires.count("y") == 1 and wires.count("w") >= 2:
				print("Cut fourth")
			elif "r" not in wires:
				print("Cut last")
			else:
				print("Cut fourth")
	if moduleType == "b" or moduleType == "button" or moduleType == "but":
		button = input("Enter button: ")
		if button == ("ba"):
			print("Hold - Y5, B4 Else 1")
		elif button[-1] == "d" and batteries >= 2:
			print("Tap")
		elif button[0] == "w" and CAR == 1:
			print("Hold - Y5, B4 Else 1")
		elif batteries >= 2 and FRK == 1:
			print("Tap")
		elif button[0] == "r":
			print("Tap")
		else:
			print("Hold - Y5, B4 Else 1")
	if moduleType == "mem":
		memlist = [" "]*8
		s1n = int(input("Stage 1 number? "))
		if s1n == 1 or s1n == 2:
			print("Second Position")
			memlist[0] = "2"
		elif s1n == 3:
			print("Third Position")
			memlist[0] = "3"
		else:
			print("Fourth Position")
			memlist[0] = "4"
		memlist[1]=input("What was the value? ")
		s2n = int(input("Stage 2 number? "))
		if s2n == 1:
			print("Number 4")
			memlist[3] = "4"
			memlist[2] = input("What was the position? ")
		elif s2n == 2 or s2n == 4:
			print("Position " + memlist[0])
			memlist[2] = memlist[0]
			memlist[3] = input("What was the value? ")
		else:
			print("First Position")
			memlist[2] = 1
			memlist[3] = input("What was the value? ")
		s3n = int(input("Stage 3 number? "))
		if s3n == 1:
			print("Number " + memlist[3])
			memlist[5] = memlist[3]
			memlist[4] = input("what was the position? ")
		elif s3n == 2:
			print("Number " + memlist[1])
			memlist[5] = memlist[1]
			memlist[4] = input("what was the position? ")
		elif s3n == 3:
			print("Third Position")
			memlist[4] = 3
			memlist[5] = input("What was the value? ")
		else:
			print("Number four")
			memlist[5] = 4
			memlist[4] = input("what was the position? ")
		s4n = int(input(" Stage 4 number? "))
		if s4n == 1:
			print("Position " + memlist[0])
			memlist[6] = memlist[0]
			memlist[7] = input("What was the value? ")
		elif s4n == 2:
			print("First Position")
			memlist[6] = 1
			memlist[7] = input("what was the value? ")
		elif s4n == 3 or s4n == 4:
			print("Position" + memlist[2])
			memlist[6] = memlist[2]
			memlist[7] = input("what was the value? ")
		s5n = int(input("Stage 5 Number? "))
		if s5n == 1:
			print("Number " + memlist[1])
		elif s5n == 2:
			print("Number " + memlist[3])
		elif s5n == 3:
			print("Number " + memlist[7])
		elif s5n == 4:
			print("Number " + memlist[5])
	if moduleType == "p":
		third = input("Third letters: ")
		fifth = input("Fifth letters: ")


		words = ["about", "after", "again", "below", "could", "every", "first", "found", "great", "house", "large", "learn", "never", "other", "place", "plant", "point", "right", "small", "sound", "spell", "still", "study", "their", "there", "these", "thing", "think", "three", "water", "where", "which", "world", "would", "write"]
		for word in words:
			if (word[2] in third):
				if(word[4] in fifth):
					print(word)
	if moduleType == "cw":
		finished = False
		while not finished:
			cwire = input("complicated wire: ")
			if cwire == "rsl" or cwire == "rl" or cwire == "sl" or cwire == "rls" or cwire == "ls":
				if batteries >= 2:
					print("Cut the wire")
				else:
					print("Don't cut the wire")
			elif cwire == "n" or cwire == "s" or cwire == "rs":
				print("cut the wire")
			elif cwire == "r" or cwire == "b" or cwire == "rb" or cwire == "rbl" or cwire == "br" or cwire == "brl":
				if even == 1:
					print("Cut the wire")
				else:
					print("Don't cut the wire")
			elif cwire == "bsl" or cwire == "bl" or cwire == "rbs" or cwire == "bls" or cwire == "brs":
				if parallel == 1:
					print("Cut the wire")
				else:
					print ("Dont cut the wire")
			elif cwire == "l" or cwire == "bs" or cwire == "rbsl" or cwire == "brsl" or cwire == "brls"or cwire == "rbls":
				print("Don't cut the wire")
			elif cwire == "f" or cwire == "finish":
				finished = True
	if moduleType == "ws":
		rnum = int(1) 
		bnum = int(1) 
		Bnum = int(1)
		wsfinish = False
		while not wsfinish:
			seqWire = input("What is the wire? ")
			if seqWire == "bb" and bnum in [1,3,5,6]:
				print("Cut the wire")
				bnum += 1
			elif seqWire == "ba" and bnum in [2,4,8,9]:
				print("Cut the wire")
				bnum += 1
			elif seqWire ==  "bc" and bnum in [2,6,7,8]:
				print("Cut the wire")
				bnum += 1 
			elif seqWire == "ra" and rnum in [3,4,6,7,8]:
				print("Cut the wire")
				rnum += 1
			elif seqWire == "rb" and rnum in [2,5,7,8,9]:
				print("Cut the wire")
				rnum +=1
			elif seqWire == "rc" and rnum in [1,4,6,7]:
				print("Cut the wire")
				rnum += 1
			elif seqWire == "Ba" and rnum in [1,2,4,7]:
				print("Cut the wire")
				rnum += 1
			elif seqWire == "Bb" and rnum in [1,3,5,6,7]:
				print("Cut the wire")
				rnum +=1
			elif seqWire == "Bc" and rnum in [1,2,4,6,8,9]:
				print("Cut the wire")
				rnum += 1
			elif seqWire[0] == "r":
				print("Don't cut the wire")
				rnum += 1
			elif seqWire[0] == "b":
				print("Don't cut the wire")
				bnum+= 1
			elif seqWire[0] == "B":
				print("Don't cut the wire")
				Bnum += 1
			elif seqWire == "f" or seqWire == "finish":
				wsfinish = True
	if moduleType == "sym"	or moduleType == "s" or moduleType == "symbols":
		symbols1 = ["balloon", "tina", "lambda", "lightning", "cat", "x,", "backc"]
		symbols2 = ["euro", "balloon", "backc", "cq", "estar", "x,", "?"]
		symbols3 = ["cr", "w", "cq", "xi", "3melt", "lambda", "estar"]
		symbols4 = ["6", "pg", "bt", "cat", "xi", "?", "smile"]
		symbols5 = ["candle", "smile", "bt", "cdot", "pg", "alien3", "fstar"]
		symbols6 = ["6", "euro" "!=", "ae", "candle", "backn", "omega"]
		symbols = input("Symbols: ")
		symbollist = []
		for symbol in symbols1:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
		for symbol in symbols2:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
		for symbol in symbols3:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
		for symbol in symbols4:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
		for symbol in symbols5:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
		for symbol in symbols6:
			if symbol in symbols:
				symbollist.append(symbol)		
		if len(symbollist) == 4:
			print(symbollist)
		else:
			symbollist = []
	if moduleType == "ss":
			if vowel == 1:
				if strikes = 0:
					print("Swapped Red and Blue and Swapped Green and Yellow")
			else: 
				if strikes = 0:
					print("Clockwise Rotation excluding green")
	if moduleType == "wof":
		while not woffinished:
			display = input("Display word")
			if display == "ur":
				smallword = "top left"
			elif display == "first" or display == "okay" or display == "c"
				smallword = "top right"
			elif display == "nothing" or display == "led" or display == "they are":
				smallword = "middle left"
			elif display == "blank" or display == "read" or display == "red" or display == "you"
			 or display == "your" or display == "you're" or display == "youre" or display == "their":
				smallword = "middle right"
			elif display == "empty" or "reed" or display == "leed" or display == "they're" or display == "theyre"
			elif display == "display" or display == "says" or display == "no" or display == "lead" or display == "hold on" or display == "you are" or display == "there" or display == "see" or display == "cee"
			listword = input("what is " + smallword + "? ")