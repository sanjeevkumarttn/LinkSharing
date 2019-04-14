from Tkinter import *
import Tkinter as ttk
from ttk import *
import webscrap

root = Tk()
root.title("Webscrapper")
 
# Add a grid
mainframe = Frame(root)
mainframe.grid(column=0,row=0, sticky=(N,W,E,S) )
mainframe.columnconfigure(0, weight = 1)
mainframe.rowconfigure(0, weight = 1)
mainframe.pack(pady = 50, padx = 50)
 
# Create a Tkinter variable
list1 = StringVar(root)
list2 = StringVar(root)
 
# Dictionary with options
PRODUCTS = { 'Mobile','Laptops'}
list1.set('Mobile') # set the default option

MOBILES={'MI','Apple','Samsung'}
list2.set('MI') # set the default option
 
popupMenu = OptionMenu(mainframe,list1, *PRODUCTS)
Label(mainframe, text="Products").grid(row = 1, column = 1)
popupMenu.grid(row = 2, column =1)

popupMenu = OptionMenu(mainframe, list2, *MOBILES)
Label(mainframe, text="Mobiles").grid(row = 1, column = 3)
popupMenu.grid(row = 2, column =3)

button = Button(root, text="OK", command=ok)
button.pack()
 
def ok():
    	#print ("value is:" + list1.get())
	#print ("value is:" + list2.get())
	mainMethod(list1.get(),list1.get())



root.mainloop()
