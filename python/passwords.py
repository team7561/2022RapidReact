third = input("Third letters: ")
fifth = input("Fifth letters: ")


words = ["about", "after", "again", "below", "could", "every", "first", "found", "great", "house", "large", "learn", "never", "other", "place", "plant", "point", "right", "small", "sound", "spell", "still", "study", "their", "there", "these", "thing", "think", "three", "water", "where", "which", "world", "would", "write"]
for word in words:
	if (word[2] in third):
		if(word[4] in fifth):
			print(word)