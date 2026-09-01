/* eslint-disable react/prop-types */
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

import {ChevronDownIcon, DotsVerticalIcon} from "@radix-ui/react-icons";


import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,

  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import UserList from "./UserList";
import { useDispatch } from "react-redux";
import { deleteIssue } from "@/redux/Issue/Issue.action";
import { useNavigate, useParams } from "react-router-dom";

const IssueCard = ({item}) => {
  const dispatch=useDispatch();
  const {id}=useParams()
  const navigate=useNavigate();

  const handleDelete=()=>{
    dispatch(deleteIssue(item.id))
  }
  return (
      <Card className="rounded-md py-1 pb-2">
        <CardHeader className="py-0 pb-1">
          <div className="flex justify-between items-center">
            <CardTitle className="cursor-pointer hover:text-gray-300" onClick={()=>navigate(`/project/${id}/issue/${item.id}`)}>{item.title}</CardTitle>

            <DropdownMenu>
              <DropdownMenuTrigger>
                {" "}
                <Button
                    className="rounded-full focus:outline-none"
                    variant="ghost"
                    size="icon"
                >
                  <DotsVerticalIcon />{" "}
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuItem onClick={handleDelete}>Delete</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </CardHeader>
        <CardContent className="py-0">
          <div className="flex items-center justify-between">

            <DropdownMenu className="w-[30rem]">
              <DropdownMenuTrigger asChild>
                <button
                    type="button"
                    className="inline-flex items-center gap-3 rounded-full border bg-gray-900 text-white px-3 py-1.5 text-sm"
                    title={item.assignee?.fullName ?? "Unassigned"}
                >
                  <span className="max-w-[14rem] truncate">{item.assignee?.fullName ?? "Unassigned"}</span>
                  <ChevronDownIcon className="w-3 h-3" />
                </button>
              </DropdownMenuTrigger>

              <DropdownMenuContent>
                <UserList issueDetails={item} />
              </DropdownMenuContent>
            </DropdownMenu>

          </div>
        </CardContent>
      </Card>
  );
};

export default IssueCard;
