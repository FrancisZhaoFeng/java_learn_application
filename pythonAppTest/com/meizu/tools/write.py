def write_info(strContent):
    f=file("/tmp/hello","a+")
    f.writelines(strContent+"\n")
    f.close()
    
if __name__ =="__main__":
    for i in range(0,100):
        write_info("hello world:%d"%i)